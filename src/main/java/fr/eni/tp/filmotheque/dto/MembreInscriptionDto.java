package fr.eni.tp.filmotheque.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class MembreInscriptionDto {

    @NotBlank
    @Size(min = 2, max=50)
    private String nom;


    @NotBlank
    @Size(min = 2, max=50)
    private String prenom;

    @NotBlank
    @Size(min = 5, max=20)
    private String pseudo;

    @NotBlank
    @Size(min = 5)
    private String motDePasse;


    public MembreInscriptionDto() {
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

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

}
