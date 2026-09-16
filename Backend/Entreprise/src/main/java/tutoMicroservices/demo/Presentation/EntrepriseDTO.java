package tutoMicroservices.demo.Presentation;

import java.util.List;
import tutoMicroservices.demo.Application.Taille;

public class EntrepriseDTO {
    private int id;
    private String nom;
    private List<EmployeDTO> employes;
    private String secteur;
    private String localisation;
    private Taille taille;

    public EntrepriseDTO() {}
    public EntrepriseDTO(int id, String nom, String secteur, String localisation, Taille taille) {
        this.id = id;
        this.nom = nom;
        this.secteur = secteur;
        this.localisation = localisation;
        this.taille = taille;
    }
    public EntrepriseDTO(int id, String nom, List<EmployeDTO> employes) {
        this.id = id;
        this.nom = nom;
        this.employes = employes;
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

    public List<EmployeDTO> getEmployes() {
        return employes;
    }

    public void setEmployes(List<EmployeDTO> employes) {
        this.employes = employes;
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
    public Taille getTaille() {
        return taille;
    }
    public void setTaille(Taille taille) {
        this.taille = taille;
    }
}
