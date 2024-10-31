package com.magasinpeche.service;

import com.magasinpeche.model.Produit;
import com.magasinpeche.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProduitService {
    @Autowired
    private ProduitRepository produitRepository;

    public List<Produit> getAllProduits() {
        return produitRepository.findAllByOrderByDateCreationDesc();
    }

    public List<Produit> getDerniersProduits() {
        return produitRepository.findTop3AllByOrderByDateCreationDesc();
    }

    public Produit getProduitById(Long id) {
        return produitRepository.findById(id).orElse(null);
    }

    public Produit createProduit(Produit produit) {
        produit.setDateCreation(LocalDateTime.now()); // set de la date de création
        return produitRepository.save(produit);
    }

    public Produit updateProduit(Long id, Produit produitDetails) {
        Produit produit = produitRepository.findById(id).orElse(null);
        if (produit != null) {
            produit.setNom(produitDetails.getNom());
            produit.setDescription(produitDetails.getDescription());
            produit.setPrix(produitDetails.getPrix());
            produit.setQuantite(produitDetails.getQuantite());
            produit.setCategorie(produitDetails.getCategorie());
            produit.setImageUrl(produitDetails.getImageUrl());
            return produitRepository.save(produit);
        }
        return null;
    }

    public void deleteProduit(Long id) {
        produitRepository.deleteById(id);
    }
}
