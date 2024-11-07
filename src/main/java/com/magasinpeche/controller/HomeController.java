package com.magasinpeche.controller;

import com.magasinpeche.model.Concours;
import com.magasinpeche.model.Produit;
import com.magasinpeche.repository.ConcoursRepository;
import com.magasinpeche.service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private ProduitService produitService;

    @Autowired
    private ConcoursRepository concoursRepository;

    @GetMapping("/")
    public String afficherAccueil(Model model) {
        List<Produit> derniersProduits = produitService.getDerniersProduits(); // on récupère tous les derniers produits
        List<Produit> produitsLimites = derniersProduits.stream().limit(3).toList(); // on limite les produits récupérés à 3 produits
        model.addAttribute("produits", produitsLimites);

        // Obtenir la date actuelle
        LocalDate today = LocalDate.now();

        // Récupérer les 3 concours ayant la date la plus proche
        List<Concours> concoursProchains = concoursRepository.findTop3ByDateGreaterThanOrderByDateAsc(today);

        // Ajouter les concours à la vue
        model.addAttribute("concours", concoursProchains);

        return "home";
    }
}
