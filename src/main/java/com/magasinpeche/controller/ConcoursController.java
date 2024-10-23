package com.magasinpeche.controller;

import com.magasinpeche.model.Concours;
import com.magasinpeche.service.ConcoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/concours")
public class ConcoursController {
    @Autowired
    private ConcoursService concoursService;

    @GetMapping
    public List<Concours> getAllConcours() {
        return concoursService.getAllConcours();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Concours> getConcoursById(@PathVariable Long id) {
        Concours concours = concoursService.getConcoursById(id);
        if (concours != null) {
            return ResponseEntity.ok(concours);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Concours createConcours(@RequestBody Concours concours) {
        return concoursService.createConcours(concours);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Concours> updateConcours(@PathVariable Long id, @RequestBody Concours concoursDetails) {
        Concours updatedConcours = concoursService.updateConcours(id, concoursDetails);
        if (updatedConcours != null) {
            return ResponseEntity.ok(updatedConcours);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConcours(@PathVariable Long id) {
        concoursService.deleteConcours(id);
        return ResponseEntity.noContent().build();
    }
}
