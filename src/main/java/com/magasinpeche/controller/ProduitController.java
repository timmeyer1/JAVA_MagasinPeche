package com.magasinpeche.controller;

import com.magasinpeche.model.Produit;
import com.magasinpeche.service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/produits")
public class ProduitController {
    @Autowired
    private ProduitService produitService;

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
    public String createProduit(@ModelAttribute Produit produit) {
        produitService.createProduit(produit);
        return "redirect:/produits"; // Redirige vers la liste des produits
    }

    @GetMapping("/modifier/{id}")
    public String afficherFormulaireModification(@PathVariable Long id, Model model) {
        Produit produit = produitService.getProduitById(id);
        if (produit != null) {
            model.addAttribute("produit", produit);
            return "produits/modifier_produit"; // Chemin vers le template
        }
        return "redirect:/produits"; // Redirige vers la liste si le produit n'existe pas
    }

    @PostMapping("/{id}")
    public String updateProduit(@PathVariable Long id, @ModelAttribute Produit produitDetails) {
        produitService.updateProduit(id, produitDetails);
        return "redirect:/produits"; // Redirige vers la liste des produits
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
