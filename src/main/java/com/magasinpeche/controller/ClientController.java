package com.magasinpeche.controller;

import com.magasinpeche.model.Client;
import com.magasinpeche.model.Concours;
import com.magasinpeche.model.Permis;
import com.magasinpeche.repository.ConcoursRepository;
import com.magasinpeche.service.ClientService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
            Principal principal,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request) {

        String currentEmail = principal.getName();
        Client client = clientService.findByEmail(currentEmail)
                .orElseThrow(() -> new NoSuchElementException("Client non trouvé pour l'email : " + currentEmail));

        // Sauvegarder l'email actuel avant de le changer
        String previousEmail = client.getEmail();

        // Mettre à jour les informations du client
        client.setPrenom(prenom);
        client.setNom(nom);
        client.setTelephone(telephone);
        client.setEmail(email);  // Modification de l'email
        client.setAdresse(adresse);

        // Sauvegarder les changements dans la base de données
        clientService.save(client);

        // Mettre à jour le contexte de sécurité avec le nouvel email
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            // Créer un nouvel objet Authentication avec le nouvel email
            UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(email, authentication.getCredentials(), authentication.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(newAuth);
        }

        // Ajouter un message de succès et rediriger vers /profil avec le nouvel email
        redirectAttributes.addFlashAttribute("successMessage", "Votre profil a été mis à jour avec succès !");
        return "redirect:/profil";
    }


    // page de logout
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }
}
