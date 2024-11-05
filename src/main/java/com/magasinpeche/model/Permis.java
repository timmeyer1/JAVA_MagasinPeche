package com.magasinpeche.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Permis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Enumerated(EnumType.STRING)
    private StatutPermis statut;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDemande;

    private String commentaires;

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

    public StatutPermis getStatut() {
        return statut;
    }

    public void setStatut(StatutPermis statut) {
        this.statut = statut;
    }

    public Date getDateDemande() {
        return dateDemande;
    }

    public void setDateDemande(Date dateDemande) {
        this.dateDemande = dateDemande;
    }

    public String getCommentaires() {
        return commentaires;
    }

    public void setCommentaires(String commentaires) {
        this.commentaires = commentaires;
    }
}
