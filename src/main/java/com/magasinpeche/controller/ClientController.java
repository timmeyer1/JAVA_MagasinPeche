package com.magasinpeche.controller;

import com.magasinpeche.model.Client;
import com.magasinpeche.model.Concours;
import com.magasinpeche.model.Permis;
import com.magasinpeche.repository.ConcoursRepository;
import com.magasinpeche.service.ClientService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private ConcoursRepository concoursRepository;

    // register
    @GetMapping("/register")
    public String showInscriptionForm(Model model, HttpServletRequest request) {
        model.addAttribute("client", new Client());
        if (request.getUserPrincipal() != null) {
            return "redirect:/"; // si user connecté -> accueil
        }
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute Client client) {
        clientService.save(client);
        return "redirect:/login";
    }

    // login
    @GetMapping("/login")
    public String showConnexionForm(HttpServletRequest request) {
        if (request.getUserPrincipal() != null) {
            return "redirect:/"; // si user connecté -> accueil
        }
        return "login";
    }

    // page de profil
    @GetMapping("/profil")
    public String profil(Model model, Principal principal) {
        if (principal != null) {
            String email = principal.getName();
            Client client = clientService.findByEmail(email).orElse(null);
            model.addAttribute("client", client);

            // Récupération du permis pour le client actuel
            Permis permis = (client != null) ? client.getPermis() : null;
            model.addAttribute("permis", permis); // Ajouter le permis au modèle

            // Récupérer tous les concours auxquels le client est inscrit
            List<Concours> participations = concoursRepository.findConcoursByClient(client);
            model.addAttribute("concours", participations);
        }
        return "profil/profil";
    }

    // page de logout
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }
}
