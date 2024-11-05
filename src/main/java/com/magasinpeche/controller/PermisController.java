package com.magasinpeche.controller;

import com.magasinpeche.model.Client;
import com.magasinpeche.model.Permis;
import com.magasinpeche.model.StatutPermis;
import com.magasinpeche.service.ClientService;
import com.magasinpeche.service.PermisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/permis")
public class PermisController {
    @Autowired
    private PermisService permisService;

    @Autowired
    private ClientService clientService;

    @GetMapping("/demande")
    public String demandePermisForm(Model model, Principal principal) {
        Client client = clientService.findByEmail(principal.getName()).orElse(null);
        model.addAttribute("permis", new Permis());
        model.addAttribute("clientId", client.getId());
        return "permis/demande"; // Page Thymeleaf pour le formulaire
    }

    @PostMapping("/demande")
    public String soumettreDemande(@ModelAttribute Permis permis, @RequestParam Long clientId) {
        Client client = clientService.findById(clientId).orElse(null);
        permis.setClient(client);
        permisService.save(permis);
        return "redirect:/profil"; // Redirige vers la liste des demandes
    }

    @GetMapping("/liste")
    public String listDemandes(Model model) {
        model.addAttribute("demandes", permisService.findAll());
        return "permis/list"; // Page Thymeleaf pour afficher la liste des demandes
    }

    @PostMapping("/traiter/{id}")
    public String traiterDemande(@PathVariable Long id, @RequestParam StatutPermis statut) {
        permisService.updateStatut(id, statut);
        // Appel à une méthode pour envoyer une notification par email
        return "redirect:/permis/liste"; // Redirige vers la liste des demandes
    }
}
