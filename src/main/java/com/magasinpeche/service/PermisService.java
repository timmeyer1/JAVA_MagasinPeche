package com.magasinpeche.service;

import com.magasinpeche.model.Permis;
import com.magasinpeche.repository.PermisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermisService {
    @Autowired
    private PermisRepository permisRepository;

    public List<Permis> getAllPermis() {
        return permisRepository.findAll();
    }

    public Permis getPermisById(Long id) {
        return permisRepository.findById(id).orElse(null);
    }

    public Permis createPermis(Permis permis) {
        return permisRepository.save(permis);
    }

    public Permis updatePermis(Long id, Permis permisDetails) {
        Permis permis = permisRepository.findById(id).orElse(null);
        if (permis != null) {
            permis.setClient(permisDetails.getClient());
            permis.setDateDemande(permisDetails.getDateDemande());
            permis.setStatut(permisDetails.getStatut());
            return permisRepository.save(permis);
        }
        return null;
    }

    public void deletePermis(Long id) {
        permisRepository.deleteById(id);
    }
}
