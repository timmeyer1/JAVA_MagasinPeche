package com.magasinpeche.controller;

import com.magasinpeche.model.Produit;
import com.magasinpeche.service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private ProduitService produitService;

    @GetMapping("/")
    public String afficherAccueil(Model model) {
        List<Produit> produits = produitService.getAllProduits(); // on récupère tous les produits
        List<Produit> produitsLimites = produits.stream().limit(3).toList(); // on limite les produits récupérés à 3 produits

        model.addAttribute("produits", produitsLimites);
        return "home";
    }
}
