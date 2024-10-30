package com.magasinpeche.controller;

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
@RequestMapping("/produits")
public class ProduitController {
    @Autowired
    private ProduitService produitService;

    private static final String UPLOAD_DIR = "uploads/";

    @GetMapping
    public String getAllProduits(Model model) {
        List<Produit> produits = produitService.getAllProduits();
        model.addAttribute("produits", produits);
        return "produits/liste_produits"; // Chemin vers le template
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produit> getProduitById(@PathVariable Long id) {
        Produit produit = produitService.getProduitById(id);
        if (produit != null) {
            return ResponseEntity.ok(produit);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/ajouter")
    public String afficherFormulaireAjout(Model model) {
        model.addAttribute("produit", new Produit());
        return "produits/ajouter_produit"; // Chemin vers le template
    }

    @PostMapping("/ajouter")
    public String createProduit(@ModelAttribute Produit produit, @RequestParam("imageFile") MultipartFile imageFile) {
        if (!imageFile.isEmpty()) {
            try {
                String originalFileName = imageFile.getOriginalFilename();
                Path filePath = Paths.get(UPLOAD_DIR + originalFileName);
                int count = 1;

                // Vérifier si le fichier existe déjà et générer un nouveau nom si nécessaire
                while (Files.exists(filePath)) {
                    String newFileName = originalFileName.replaceFirst("(\\.[^\\.]+)$", "_" + count + "$1"); // Ajoute un suffixe
                    filePath = Paths.get(UPLOAD_DIR + newFileName);
                    count++;
                }

                // Enregistrer le fichier
                Files.copy(imageFile.getInputStream(), filePath);
                produit.setImageUrl("/" + UPLOAD_DIR + filePath.getFileName().toString());  // Définir l'URL de l'image
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        produitService.createProduit(produit);
        return "redirect:/produits";
    }


    @GetMapping("/modifier/{id}")
    public String afficherFormulaireModification(@PathVariable("id") Long id, Model model) {
        Produit produit = produitService.getProduitById(id);
        if (produit != null) {
            model.addAttribute("produit", produit);
            return "produits/modifier_produit"; // Chemin vers le template
        }
        return "redirect:/produits"; // Redirige vers la liste si le produit n'existe pas
    }

    @PostMapping("/{id}") // Modifiez le chemin pour qu'il corresponde au formulaire
    public String updateProduit(@PathVariable("id") Long id,
                                @ModelAttribute Produit produitDetails,
                                @RequestParam("imageFile") MultipartFile imageFile) {
        // Récupérer le produit existant pour conserver l'ancienne image si aucune nouvelle image n'est fournie
        Produit produitExist = produitService.getProduitById(id);

        if (produitExist != null) {
            // Vérifier si une nouvelle image a été téléchargée
            if (!imageFile.isEmpty()) {
                try {
                    String originalFileName = imageFile.getOriginalFilename();
                    Path filePath = Paths.get(UPLOAD_DIR + originalFileName);
                    int count = 1;

                    // Vérifier si le fichier existe déjà et générer un nouveau nom si nécessaire
                    while (Files.exists(filePath)) {
                        String newFileName = originalFileName.replaceFirst("(\\.[^\\.]+)$", "_" + count + "$1"); // Ajoute un suffixe
                        filePath = Paths.get(UPLOAD_DIR + newFileName);
                        count++;
                    }

                    // Enregistrer le fichier
                    Files.copy(imageFile.getInputStream(), filePath);
                    produitDetails.setImageUrl("/" + UPLOAD_DIR + filePath.getFileName().toString()); // Mettre à jour avec le nouveau nom
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // Si aucune nouvelle image, conserver l'ancienne image
                produitDetails.setImageUrl(produitExist.getImageUrl());
            }

            // Mettre à jour le produit avec les détails (y compris l'image)
            produitService.updateProduit(id, produitDetails);
        }

        return "redirect:/produits"; // Rediriger vers la liste des produits
    }

    @GetMapping("/supprimer/{id}")
    public String supprimerProduit(@PathVariable Long id) {
        produitService.deleteProduit(id);
        return "produits/confirmation_suppression"; // Affiche une page de confirmation de suppression
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduit(@PathVariable Long id) {
        produitService.deleteProduit(id);
        return ResponseEntity.noContent().build();
    }
}
