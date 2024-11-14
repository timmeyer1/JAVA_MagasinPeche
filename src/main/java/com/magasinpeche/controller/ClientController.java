package com.magasinpeche.controller;

import com.magasinpeche.model.*;
import com.magasinpeche.repository.ConcoursRepository;
import com.magasinpeche.repository.PanierRepository;
import com.magasinpeche.repository.ProduitRepository;
import com.magasinpeche.service.ClientService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private ConcoursRepository concoursRepository;

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private PanierRepository panierRepository;  // Répository Panier

    // ------------------------------------------------------------- AUTHENTICATION -------------------------------------------------------------

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

    // ------------------------------------------------------------- PROFIL -------------------------------------------------------------

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

            // Vérifie si le client a un panier
            Panier panier = panierRepository.findByClient(client).orElse(new Panier());
            model.addAttribute("panier", panier);

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

        // Vérification si l'email est déjà utilisé par un autre utilisateur
        if (!email.equals(previousEmail) && clientService.existsByEmail(email)) {
            // Si l'email est déjà utilisé, ajouter un message d'erreur et rediriger vers le profil
            redirectAttributes.addFlashAttribute("errorMessage", "❌ Cet email est déjà utilisé par un autre utilisateur.");
            return "redirect:/profil";
        }

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
        redirectAttributes.addFlashAttribute("successMessage", "✅ Votre profil a été mis à jour avec succès !");
        return "redirect:/profil";
    }

    // ------------------------------------------------------------- Gestion du stock -------------------------------------------------------------
    // Ajoute un produit au panier et met à jour le stock
    @PostMapping("/add-to-cart/{produitId}")
    public String addToCart(@PathVariable Long produitId, Principal principal) {
        String email = principal.getName();
        Client client = clientService.findByEmail(email).orElse(null);

        if (client != null) {
            // Récupérer le panier existant du client ou en créer un nouveau
            Panier panier = panierRepository.findByClient(client).orElse(new Panier());

            // Si le panier n'existe pas, on l'associe au client
            if (panier.getId() == null) {
                panier.setClient(client); // Associer le panier au client
            }

            // Récupérer le produit
            Produit produit = produitRepository.findById(produitId).orElse(null);

            if (produit != null && produit.getQuantite() > 0) {
                panier.addProduit(produit); // Ajouter le produit au panier
                produit.setQuantite(produit.getQuantite() - 1); // Réduire le stock du produit
                produitRepository.save(produit); // Sauvegarder le produit avec stock mis à jour
                panierRepository.save(panier); // Sauvegarder ou mettre à jour le panier du client
            }
        }

        return "redirect:/shop"; // Redirige vers la page de profil
    }

    // Retire un produit du panier et met à jour le stock
    @PostMapping("/remove-from-cart/{produitId}")
    public String removeFromCart(@PathVariable Long produitId, Principal principal) {
        String email = principal.getName();
        Client client = clientService.findByEmail(email).orElse(null);

        if (client != null) {
            Panier panier = panierRepository.findByClient(client).orElse(null);
            if (panier != null) {
                Produit produit = produitRepository.findById(produitId).orElse(null);
                if (produit != null && panier.getProduits().contains(produit)) {
                    panier.removeProduit(produit); // Retirer le produit du panier
                    produit.setQuantite(produit.getQuantite() + 1); // Augmenter le stock
                    produitRepository.save(produit); // Sauvegarder le produit
                    panierRepository.save(panier); // Sauvegarder le panier
                }
            }
        }

        return "redirect:/profil?=produits"; // Redirige vers la page de profil
    }

// ------------------------------------------------------------- LOGOUT -------------------------------------------------------------

    // page de logout
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }
}
