package com.magasinpeche.service;

import com.magasinpeche.model.Permis;
import com.magasinpeche.model.Statut;
import com.magasinpeche.repository.PermisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PermisService {
    @Autowired
    private PermisRepository permisRepository;

    public List<Permis> findAll() {
        return permisRepository.findAll();
    }

    public Permis findById(Long id) {
        return permisRepository.findById(id).orElse(null);
    }

    public Permis save(Permis permis) {
        permis.setDateDemande(new Date());
        permis.setStatut(Statut.EN_ATTENTE);
        return permisRepository.save(permis);
    }

    public void updateStatut(Long id, Statut statut) {
        Permis permis = findById(id);
        if (permis != null) {
            permis.setStatut(statut);
            permisRepository.save(permis);
            // Appel à l'envoi de l'email
        }
    }

    public List<Permis> findAllOrderedByDate() {
        return permisRepository.findAllByOrderByDateDemandeDesc();
    }
}
