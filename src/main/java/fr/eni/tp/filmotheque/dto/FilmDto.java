package fr.eni.tp.filmotheque.dto;

import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FilmDto {

    @Size(min = 5, max=50)
    private String titre;

    @Min(value = 1888, message = "L'année doit être supérieure ou égale à 1888")
    @Max(value = 2100, message = "L'année doit être inférieure ou égale à 2100")
    private Integer annee;

    private Integer duree;

    @NotNull
    private String synopsis;

    private Long idGenre;

    private Long idRealisateur;

    private List<Long> idsActeurs;

    public FilmDto() {
        this.idsActeurs = new ArrayList<>();
    }

    public FilmDto(String titre, Integer annee, Integer duree, String synopsis, Long idGenre, Long idRealisateur) {
        this.titre = titre;
        this.annee = annee;
        this.duree = duree;
        this.synopsis = synopsis;
        this.idGenre = idGenre;
        this.idRealisateur = idRealisateur;
    }

    @Override
    public String toString() {
        return "FilmDto{" +
                "titre='" + titre + '\'' +
                ", annee=" + annee +
                ", duree=" + duree +
                ", synopsis='" + synopsis + '\'' +
                ", idGenre=" + idGenre +
                ", idRealisateur=" + idRealisateur +
                ", idsActeurs=" + idsActeurs +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof FilmDto filmDto)) return false;
        return Objects.equals(titre, filmDto.titre) && Objects.equals(annee, filmDto.annee) && Objects.equals(duree, filmDto.duree) && Objects.equals(synopsis, filmDto.synopsis) && Objects.equals(idGenre, filmDto.idGenre) && Objects.equals(idRealisateur, filmDto.idRealisateur) && Objects.equals(idsActeurs, filmDto.idsActeurs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titre, annee, duree, synopsis, idGenre, idRealisateur, idsActeurs);
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Integer getAnnee() {
        return annee;
    }

    public void setAnnee(Integer annee) {
        this.annee = annee;
    }

    public Integer getDuree() {
        return duree;
    }

    public void setDuree(Integer duree) {
        this.duree = duree;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public Long getIdGenre() {
        return idGenre;
    }

    public void setIdGenre(Long idGenre) {
        this.idGenre = idGenre;
    }

    public Long getIdRealisateur() {
        return idRealisateur;
    }

    public void setIdRealisateur(Long idRealisateur) {
        this.idRealisateur = idRealisateur;
    }

    public List<Long> getIdsActeurs() {
        return idsActeurs;
    }

    public void setIdsActeurs(List<Long> idsActeurs) {
        this.idsActeurs = idsActeurs;
    }
}
