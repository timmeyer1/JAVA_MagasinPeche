package com.magasinpeche.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Permis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    private LocalDateTime dateDemande;
    private String statut; // 'en_attente', 'approuve', 'rejeté'

    // Constructeurs
    public Permis() {
    }

    public Permis(Client client, LocalDateTime dateDemande, String statut) {
        this.client = client;
        this.dateDemande = dateDemande;
        this.statut = statut;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public LocalDateTime getDateDemande() {
        return dateDemande;
    }

    public void setDateDemande(LocalDateTime dateDemande) {
        this.dateDemande = dateDemande;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
}
