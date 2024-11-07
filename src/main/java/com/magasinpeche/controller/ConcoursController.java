package com.magasinpeche.controller;

import com.magasinpeche.model.Client;
import com.magasinpeche.model.Concours;
import com.magasinpeche.model.Participation;
import com.magasinpeche.model.Statut;
import com.magasinpeche.repository.ClientRepository;
import com.magasinpeche.repository.ConcoursRepository;
import com.magasinpeche.repository.ParticipationRepository;
import com.magasinpeche.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/concours")
public class ConcoursController {

    @Autowired
    private ConcoursRepository concoursRepository;

    @Autowired
    private ParticipationRepository participationRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientService clientService;

    // 1. Afficher la liste des concours disponibles
    @GetMapping
    public String afficherConcours(Model model, Authentication authentication) {
        // Récupérer l'utilisateur actuellement authentifié
        String emailClient = authentication.getName(); // Récupère l'email du client connecté
        Client client = clientRepository.findByEmail(emailClient)
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));

        // Récupérer tous les concours
        List<Concours> concoursList = concoursRepository.findAllByOrderByDateDesc();

        // Vérifier pour chaque concours si l'utilisateur est déjà inscrit
        for (Concours concours : concoursList) {
            boolean dejaInscrit = concours.getParticipations().stream()
                    .anyMatch(participation -> participation.getClient().equals(client));
            concours.setDejaInscrit(dejaInscrit); // Nous mettons à jour l'état d'inscription de l'utilisateur
        }

        model.addAttribute("concoursList", concoursList);
        return "concours/liste";  // Vue qui contient la liste des concours
    }

    // 2. Inscrire un utilisateur à un concours
    @PostMapping("/inscrire/{id}")
    public String inscrireClient(@PathVariable Long id, Authentication authentication) {
        // Récupérer le client actuellement authentifié
        String emailClient = authentication.getName();
        Client client = clientRepository.findByEmail(emailClient).orElseThrow(() -> new RuntimeException("Client non trouvé"));

        // Récupérer le concours
        Concours concours = concoursRepository.findById(id).orElseThrow(() -> new RuntimeException("Concours non trouvé"));

        // Vérifier si le client est déjà inscrit
        boolean isAlreadyRegistered = concours.getParticipations().stream()
                .anyMatch(participation -> participation.getClient().getEmail().equals(emailClient));

        if (isAlreadyRegistered) {
            return "redirect:/concours?error=already_registered"; // Rediriger avec un message d'erreur
        }

        // Créer une nouvelle participation
        Participation participation = new Participation();
        participation.setConcours(concours);
        participation.setClient(client);  // Associer le client connecté
        participation.setStatut(Statut.EN_ATTENTE);  // Statut par défaut

        // Sauvegarder la participation dans la base de données
        participationRepository.save(participation);

        return "redirect:/concours?success=registration_success"; // Rediriger avec un message de succès
    }

    // 3. Suivre les participations d'un utilisateur
    @GetMapping("/suivi")
    public String suiviParticipations(Model model, Authentication authentication) {
        // Récupérer le client actuellement authentifié
        String emailClient = authentication.getName();  // Récupère l'email du client connecté
        Client client = clientRepository.findByEmail(emailClient).orElseThrow(() -> new RuntimeException("Client non trouvé"));

        // Récupérer tous les concours auxquels le client est inscrit
        List<Concours> participations = concoursRepository.findConcoursByClient(client);

        // Ajouter les participations au modèle pour l'affichage dans la vue
        model.addAttribute("participations", participations);

        return "concours/suivi-participations";  // Vue pour le suivi des participations (templates/concours/suivi-participations.html)
    }

    // 4. Desinscrire un utilisateur d'un concours
    @GetMapping("/desinscrire/{id}")
    public String desinscrire(@PathVariable Long id, Principal principal) {
        // Récupère le client connecté
        String email = principal.getName();
        Client client = clientService.findByEmail(email).orElse(null);

        if (client != null) {
            // Récupère le concours et supprime la participation
            Concours concours = concoursRepository.findById(id).orElse(null);
            if (concours != null) {
                Participation participation = participationRepository.findByConcoursAndClient(concours, client);
                if (participation != null) {
                    participationRepository.delete(participation);
                }
            }
        }

        return "redirect:/profil"; // Redirige vers la page de profil après désinscription
    }
}
