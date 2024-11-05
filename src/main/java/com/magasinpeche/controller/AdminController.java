package com.magasinpeche.controller;

import com.magasinpeche.model.Categorie;
import com.magasinpeche.model.Produit;
import com.magasinpeche.service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private ProduitService produitService;

    private static final String UPLOAD_DIR = "uploads/";

    @GetMapping("/produits")
    public String getAllProduits(Model model) {
        List<Produit> produits = produitService.getAllProduits();
        model.addAttribute("produits", produits);
        return "admin/produits/liste_produits";
    }

    @GetMapping("/produits/{id}")
    public ResponseEntity<Produit> getProduitById(@PathVariable Long id) {
        Produit produit = produitService.getProduitById(id);
        return produit != null ? ResponseEntity.ok(produit) : ResponseEntity.notFound().build();
    }

    @GetMapping("/produits/ajouter")
    public String afficherFormulaireAjout(Model model) {
        model.addAttribute("produit", new Produit());
        return "admin/produits/ajouter_produit";
    }

    @PostMapping("/produits/ajouter")
    public String createProduit(@ModelAttribute Produit produit, @RequestParam("imageFile") MultipartFile imageFile) {
        if (!imageFile.isEmpty()) {
            try {
                String originalFileName = imageFile.getOriginalFilename();
                Path filePath = Paths.get(UPLOAD_DIR + originalFileName);
                int count = 1;

                while (Files.exists(filePath)) {
                    String newFileName = originalFileName.replaceFirst("(\\.[^\\.]+)$", "_" + count + "$1");
                    filePath = Paths.get(UPLOAD_DIR + newFileName);
                    count++;
                }

                Files.copy(imageFile.getInputStream(), filePath);
                produit.setImageUrl("/" + UPLOAD_DIR + filePath.getFileName().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        produitService.createProduit(produit);
        return "redirect:/admin/produits"; // Redirection vers la liste des produits dans le sous-chemin admin
    }

    @GetMapping("/produits/modifier/{id}")
    public String afficherFormulaireModification(@PathVariable("id") Long id, Model model) {
        Produit produit = produitService.getProduitById(id);
        if (produit != null) {
            model.addAttribute("produit", produit);
            model.addAttribute("categorie", Categorie.values());
            return "admin/produits/modifier_produit";
        }
        return "redirect:/admin/produits";
    }

    @PostMapping("/produits/{id}")
    public String updateProduit(@PathVariable("id") Long id,
                                @ModelAttribute Produit produitDetails,
                                @RequestParam("imageFile") MultipartFile imageFile) {
        Produit produitExist = produitService.getProduitById(id);

        if (produitExist != null) {
            if (!imageFile.isEmpty()) {
                try {
                    String originalFileName = imageFile.getOriginalFilename();
                    Path filePath = Paths.get(UPLOAD_DIR + originalFileName);
                    int count = 1;

                    while (Files.exists(filePath)) {
                        String newFileName = originalFileName.replaceFirst("(\\.[^\\.]+)$", "_" + count + "$1");
                        filePath = Paths.get(UPLOAD_DIR + newFileName);
                        count++;
                    }

                    Files.copy(imageFile.getInputStream(), filePath);
                    produitDetails.setImageUrl("/" + UPLOAD_DIR + filePath.getFileName().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                produitDetails.setImageUrl(produitExist.getImageUrl());
            }

            produitService.updateProduit(id, produitDetails);
        }

        return "redirect:/admin/produits";
    }

    @GetMapping("/produits/supprimer/{id}")
    public String supprimerProduit(@PathVariable Long id) {
        produitService.deleteProduit(id);
        return "admin/produits/confirmation_suppression";
    }

    @DeleteMapping("/produits/{id}")
    public ResponseEntity<Void> deleteProduit(@PathVariable Long id) {
        produitService.deleteProduit(id);
        return ResponseEntity.noContent().build();
    }
}
