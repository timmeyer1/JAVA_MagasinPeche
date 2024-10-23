package com.magasinpeche.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    @GetMapping("/connexion")
    public String connexionForm(Model model) {
        // Retourne la page de connexion
        return "connexion"; // Assurez-vous que ce fichier existe dans le répertoire de templates
    }

    @PostMapping("/connexion")
    public String connexion(/* Paramètres pour la connexion */) {
        // Logique de connexion ici
        return "redirect:/produits"; // Redirige vers la liste des produits après connexion
    }

    @GetMapping("/inscription")
    public String inscriptionForm(Model model) {
        // Retourne la page d'inscription
        return "inscription"; // Assurez-vous que ce fichier existe dans le répertoire de templates
    }

    @PostMapping("/inscription")
    public String inscription(/* Paramètres pour l'inscription */) {
        // Logique d'inscription ici
        return "redirect:/connexion"; // Redirige vers la page de connexion après inscription
    }

    @GetMapping("/")
    public String afficherHome() {
        return "index"; // Le nom du template HTML sans l'extension
    }
}
