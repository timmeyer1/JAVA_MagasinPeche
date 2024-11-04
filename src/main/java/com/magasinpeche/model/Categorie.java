package com.magasinpeche.model;

public enum Categorie {
    CANNE_A_PECHE("Canne à pêche"),
    MOULINET("Moulinet"),
    FIL_DE_PECHE("Fil de pêche"),
    HAMECONS("Hameçons"),
    EPUISETTE("Épuisette"),
    APPATS("Appâts"),
    CHAPEAU("Chapeau"),
    VETEMENT("Vêtement"),
    GANTS("Gants"),
    SEAU("Seau"),
    COUTEAU("Couteau");

    private String displayName;

    Categorie(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}