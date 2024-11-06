package com.magasinpeche.controller;

import com.magasinpeche.model.Produit;
import com.magasinpeche.service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/shop")
public class ShopController {

    private final ProduitService produitService;

    @Autowired
    public ShopController(ProduitService produitService) {
        this.produitService = produitService;
    }

    @GetMapping
    public String getAllProduits(Model model) {
        List<Produit> produits = produitService.getAllProduits();
        model.addAttribute("produits", produits);
        return "/boutique/liste";
    }
}
