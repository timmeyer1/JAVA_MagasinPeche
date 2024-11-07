package com.magasinpeche.repository;

import com.magasinpeche.model.Participation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {

    // Rechercher les participations par email du participant
    List<Participation> findByEmailParticipant(String email);

    // Rechercher les participations d'un concours
    List<Participation> findByConcoursId(Long concoursId);
}
