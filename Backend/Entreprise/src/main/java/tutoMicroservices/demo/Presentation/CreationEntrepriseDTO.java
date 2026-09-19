package tutoMicroservices.demo.Presentation;

import java.util.List;
import tutoMicroservices.demo.Application.Taille;

public class CreationEntrepriseDTO {
    private int id;
    private String nom;
    private List<Integer> idEmployes;
    private String secteur;
    private String localisation;
    private String url;
    private Taille taille;

    public CreationEntrepriseDTO(){}

    public CreationEntrepriseDTO(int id, String nom, List<Integer> idEmployes,
                                 String secteur, String localisation, String url, Taille taille) {
        this.id = id;
        this.nom = nom;
        this.idEmployes = idEmployes;
        this.secteur = secteur;
        this.localisation = localisation;
        this.url = url;
        this.taille = taille;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public List<Integer> getIdEmployes() {
        return idEmployes;
    }
    public void setIdEmployes(List<Integer> idEmployes) {
        this.idEmployes = idEmployes;
    }
    public String getSecteur() {
        return secteur;
    }
    public void setSecteur(String secteur) {
        this.secteur = secteur;
    }
    public String getLocalisation() {
        return localisation;
    }
    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public Taille getTaille() {
        return taille;
    }
    public void setTaille(Taille taille) {
        this.taille = taille;
    }
}
