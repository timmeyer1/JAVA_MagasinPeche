package com.magasinpeche.controller;

import com.magasinpeche.model.Concours;
import com.magasinpeche.model.Participation;
import com.magasinpeche.model.Statut;
import com.magasinpeche.repository.ConcoursRepository;
import com.magasinpeche.repository.ParticipationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/concours")
public class ConcoursController {

    @Autowired
    private ConcoursRepository concoursRepository;

    // 1. Afficher la liste des concours disponibles
    @GetMapping
    public String afficherConcours(Model model) {
        List<Concours> concoursList = concoursRepository.findAllByOrderByDateDesc();
        model.addAttribute("concoursList", concoursList);
        return "concours/liste";
    }

    // 2. Formulaire d'inscription pour un concours spécifique
    @GetMapping("/inscription/{id}")
    public String afficherFormulaireInscription(@PathVariable Long id, Model model) {
        Concours concours = concoursRepository.findById(id).orElseThrow();
        model.addAttribute("concours", concours);
        return "concours/inscription";  // Vue pour inscription (templates/concours/inscription.html)
    }

    // 3. Soumettre l'inscription (rediriger vers la liste des concours)
    @PostMapping("/soumettre")
    public String soumettreInscription(@RequestParam Long concoursId,
                                       @RequestParam String nomParticipant,
                                       @RequestParam String emailParticipant) {
        Concours concours = concoursRepository.findById(concoursId).orElseThrow();

        Participation participation = new Participation();
        participation.setConcours(concours);
        participation.setNomParticipant(nomParticipant);
        participation.setEmailParticipant(emailParticipant);
        participation.setStatut(Statut.EN_ATTENTE);

        concours.getParticipations().add(participation);
        concoursRepository.save(concours);  // Sauvegarde le concours et la participation

        return "redirect:/concours";  // Redirection vers la liste des concours après l'inscription
    }

    // 4. Suivre les participations d'un utilisateur
    @GetMapping("/suivi")
    public String suiviParticipations(Model model, @RequestParam String email) {
        List<Concours> participations = concoursRepository.findParticipationsByEmailParticipant(email);
        model.addAttribute("participations", participations);
        return "concours/suivi-participations";  // Vue pour le suivi des participations (templates/concours/suivi-participations.html)
    }
}
