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
<<<<<<< HEAD

=======
    private String avantages;
    private String description;
    private int nbEmployes;
    private double noteSatisfaction;
    private double scoreRse;
    private String chiffreAffaires;
    private int dateCreation;
    private int nbAgences;
    private String paysPresents;
    private String siteWeb;
    private String lienPostuler;
>>>>>>> main
    @Enumerated(EnumType.STRING)
    private Taille taille;

    public Entreprise(){}

    public Entreprise(int id, String nom, List<Integer> idEmployes, String secteur,
                      String localisation, String url, String avantages, String description,
                      int nbEmployes, double noteSatisfaction, double scoreRse,
                      String chiffreAffaires, int dateCreation, int nbAgences,
                      String paysPresents, String siteWeb, String lienPostuler, Taille taille) {
        this.id = id;
        this.nom = nom;
        this.idEmployes = idEmployes;
        this.secteur= secteur;
        this.localisation= localisation;
        this.url = url;
        this.avantages = avantages;
        this.description = description;
        this.nbEmployes = nbEmployes;
        this.noteSatisfaction = noteSatisfaction;
        this.scoreRse = scoreRse;
        this.chiffreAffaires = chiffreAffaires;
        this.dateCreation = dateCreation;
        this.nbAgences = nbAgences;
        this.paysPresents = paysPresents;
        this.siteWeb = siteWeb;
        this.lienPostuler = lienPostuler;
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
    public String getAvantages() {
        return avantages;
    }
    public void setAvantages(String avantages) {
        this.avantages = avantages;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getNbEmployes() {
        return nbEmployes;
    }
    public void setNbEmployes(int nbEmployes) {
        this.nbEmployes = nbEmployes;
    }
    public double getNoteSatisfaction() {
        return noteSatisfaction;
    }
    public void setNoteSatisfaction(double noteSatisfaction) {
        this.noteSatisfaction = noteSatisfaction;
    }
    public double getScoreRse() {
        return scoreRse;
    }
    public void setScoreRse(double scoreRse) {
        this.scoreRse = scoreRse;
    }
    public String getChiffreAffaires() {
        return chiffreAffaires;
    }
    public void setChiffreAffaires(String chiffreAffaires) {
        this.chiffreAffaires = chiffreAffaires;
    }
    public int getDateCreation() {
        return dateCreation;
    }
    public void setDateCreation(int dateCreation) {
        this.dateCreation = dateCreation;
    }
    public int getNbAgences() {
        return nbAgences;
    }
    public void setNbAgences(int nbAgences) {
        this.nbAgences = nbAgences;
    }
    public String getPaysPresents() {
        return paysPresents;
    }
    public void setPaysPresents(String paysPresents) {
        this.paysPresents = paysPresents;
    }
    public String getSiteWeb() {
        return siteWeb;
    }
    public void setSiteWeb(String siteWeb) {
        this.siteWeb = siteWeb;
    }
    public String getLienPostuler() {
        return lienPostuler;
    }
    public void setLienPostuler(String lienPostuler) {
        this.lienPostuler = lienPostuler;
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