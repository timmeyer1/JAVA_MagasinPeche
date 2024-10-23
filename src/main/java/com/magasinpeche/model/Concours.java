package com.magasinpeche.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Concours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private LocalDate date;
    private String lieu;
    private String description;

    // Constructeurs
    public Concours() {
    }

    public Concours(String nom, LocalDate date, String lieu, String description) {
        this.nom = nom;
        this.date = date;
        this.lieu = lieu;
        this.description = description;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
