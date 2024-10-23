package com.magasinpeche.controller;

import com.magasinpeche.model.Permis;
import com.magasinpeche.service.PermisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permis")
public class PermisController {
    @Autowired
    private PermisService permisService;

    @GetMapping
    public List<Permis> getAllPermis() {
        return permisService.getAllPermis();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Permis> getPermisById(@PathVariable Long id) {
        Permis permis = permisService.getPermisById(id);
        if (permis != null) {
            return ResponseEntity.ok(permis);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Permis createPermis(@RequestBody Permis permis) {
        return permisService.createPermis(permis);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Permis> updatePermis(@PathVariable Long id, @RequestBody Permis permisDetails) {
        Permis updatedPermis = permisService.updatePermis(id, permisDetails);
        if (updatedPermis != null) {
            return ResponseEntity.ok(updatedPermis);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePermis(@PathVariable Long id) {
        permisService.deletePermis(id);
        return ResponseEntity.noContent().build();
    }
}
