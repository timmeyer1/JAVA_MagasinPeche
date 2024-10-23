package com.magasinpeche.service;

import com.magasinpeche.model.Concours;
import com.magasinpeche.repository.ConcoursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConcoursService {
    @Autowired
    private ConcoursRepository concoursRepository;

    public List<Concours> getAllConcours() {
        return concoursRepository.findAll();
    }

    public Concours getConcoursById(Long id) {
        return concoursRepository.findById(id).orElse(null);
    }

    public Concours createConcours(Concours concours) {
        return concoursRepository.save(concours);
    }

    public Concours updateConcours(Long id, Concours concoursDetails) {
        Concours concours = concoursRepository.findById(id).orElse(null);
        if (concours != null) {
            concours.setNom(concoursDetails.getNom());
            concours.setDate(concoursDetails.getDate());
            concours.setLieu(concoursDetails.getLieu());
            concours.setDescription(concoursDetails.getDescription());
            return concoursRepository.save(concours);
        }
        return null;
    }

    public void deleteConcours(Long id) {
        concoursRepository.deleteById(id);
    }
}
