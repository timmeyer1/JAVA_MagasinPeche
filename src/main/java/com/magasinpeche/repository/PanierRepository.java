package com.magasinpeche.repository;

import com.magasinpeche.model.Client;
import com.magasinpeche.model.Panier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PanierRepository extends JpaRepository<Panier, Long> {
    Optional<Panier> findByClient(Client client);
}
