package com.magasinpeche.model;

import jakarta.persistence.*;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    private String email;
    @Column(name = "password", updatable = false)
    private String password;

    private String nom;
    private String prenom;
    private String adresse;
    private String telephone;

    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL)
    private Permis permis;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", updatable = false)
    private Role role;

    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL)
    private Panier panier;

    // Getters et setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }


    // Getter et setter pour role
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }



    // Getter et setter pour permis
    public Permis getPermis() {
        return permis;
    }

    public void setPermis(Permis permis) {
        this.permis = permis;
    }


    // G&S pour Panier
    public Panier getPanier() {
        return panier;
    }

    public void setPanier(Panier panier) {
        this.panier = panier;
    }
}
