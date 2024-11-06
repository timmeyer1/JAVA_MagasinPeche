package com.magasinpeche.repository;

import com.magasinpeche.model.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProduitRepository extends JpaRepository<Produit, Long> {
    @Query("SELECT p FROM Produit p ORDER BY p.dateCreation DESC")
    List<Produit> findTop3AllByOrderByDateCreationDesc();

    List<Produit> findAllByOrderByDateCreationDesc();

    List<Produit> findByCategorie(String categorie);
}
