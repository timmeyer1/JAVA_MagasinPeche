package com.magasinpeche.repository;

import com.magasinpeche.model.Concours;
import com.magasinpeche.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConcoursRepository extends JpaRepository<Concours, Long> {

    // Trouver tous les concours auxquels un client participe
    @Query("SELECT c FROM Concours c JOIN c.participations p WHERE p.client = :client")
    List<Concours> findConcoursByClient(Client client);

    List<Concours> findAllByOrderByDateDesc();
}
