package tutoMicroservices.demo.Application;

import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import tutoMicroservices.demo.Infrastucture.JsonFileWriter;

@Entity
@EntityListeners(EntrepriseEntityListener.class)
public class Entreprise {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String nom;

    @ElementCollection
    private List<Integer> idEmployes;

    private String secteur;
    private String localisation;
    private String url;

    @Enumerated(EnumType.STRING)
    private Taille taille;

    public Entreprise(){}

    public Entreprise(int id, String nom, List<Integer> idEmployes, String secteur,
                      String localisation, String url, Taille taille) {
        this.id = id;
        this.nom = nom;
        this.idEmployes = idEmployes;
        this.secteur= secteur;
        this.localisation= localisation;
        this.url = url;
        this.taille= taille;
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

    public Taille getTaille() {
        return taille;
    }
    public void setTaille(Taille taille) {
        this.taille = taille;
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
}

// Écouteur JPA
class EntrepriseEntityListener {

    @PostPersist
    @PostUpdate
    @PostRemove
    public void onPostSave(Entreprise entreprise) {
        JsonFileWriter.exporterJson();
    }
}