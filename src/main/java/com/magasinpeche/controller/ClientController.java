package com.magasinpeche.controller;

import com.magasinpeche.model.Client;
import com.magasinpeche.model.Concours;
import com.magasinpeche.model.Permis;
import com.magasinpeche.repository.ConcoursRepository;
import com.magasinpeche.service.ClientService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;

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

    @PostMapping("/editprofile")
    public String updateProfile(
            @RequestParam String prenom,
            @RequestParam String nom,
            @RequestParam String telephone,
            @RequestParam String email,
            @RequestParam String adresse,
            Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentEmail = authentication.getName();
        Client client = clientService.findByEmail(currentEmail)
                .orElseThrow(() -> new NoSuchElementException("Client non trouvé pour l'email : " + currentEmail));

        String existingPassword = client.getPassword();


        // Mettre à jour les informations du client
        client.setPrenom(prenom);
        client.setNom(nom);
        client.setTelephone(telephone);
        client.setEmail(email);
        client.setAdresse(adresse);

        clientService.save(client);  // Sauvegarde les modifications

        model.addAttribute("client", client);  // Repasser l'objet client mis à jour au modèle
        model.addAttribute("successMessage", "Votre profil a été mis à jour avec succès !");
        return "redirect:/profil";  // Retourne à la page de profil avec les modifications
    }


    // page de logout
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }
}
