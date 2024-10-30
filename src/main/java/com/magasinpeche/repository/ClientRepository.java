package com.magasinpeche.repository;

import com.magasinpeche.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    // Requête pour trouver un client par email (nom d'utilisateur)
    Client findByEmail(String email);
}
