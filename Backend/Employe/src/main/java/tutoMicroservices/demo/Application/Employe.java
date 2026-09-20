package tutoMicroservices.demo.Application;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
public class Employe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String nom;
    private String prenom;
    private LocalDate dateArrivee;

    public Employe(){}

    public Employe(int id, String nom, String prenom, LocalDate dateArrivee) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateArrivee = dateArrivee;
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
    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public LocalDate getDateArrivee() {
        return dateArrivee;
    }
    public void setDateArrivee(LocalDate dateArrivee) {
        this.dateArrivee = dateArrivee;
    }
}
