package com.magasinpeche.service;

import com.magasinpeche.model.Commande;
import com.magasinpeche.repository.CommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommandeService {
    @Autowired
    private CommandeRepository commandeRepository;

    public List<Commande> getAllCommandes() {
        return commandeRepository.findAll();
    }

    public Commande getCommandeById(Long id) {
        return commandeRepository.findById(id).orElse(null);
    }

    public Commande createCommande(Commande commande) {
        return commandeRepository.save(commande);
    }

    public Commande updateCommande(Long id, Commande commandeDetails) {
        Commande commande = commandeRepository.findById(id).orElse(null);
        if (commande != null) {
            commande.setClient(commandeDetails.getClient());
            commande.setDateCommande(commandeDetails.getDateCommande());
            commande.setStatut(commandeDetails.getStatut());
            commande.setTotal(commandeDetails.getTotal());
            return commandeRepository.save(commande);
        }
        return null;
    }

    public void deleteCommande(Long id) {
        commandeRepository.deleteById(id);
    }
}
