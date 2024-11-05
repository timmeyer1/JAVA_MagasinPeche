package com.magasinpeche.repository;

import com.magasinpeche.model.Permis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PermisRepository extends JpaRepository<Permis, Long> {

    @Query("SELECT p FROM Permis p ORDER BY p.dateDemande DESC")
    List<Permis> findAllByOrderByDateDemandeDesc();
}
