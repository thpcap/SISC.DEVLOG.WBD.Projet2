package tutoMicroservices.demo.Application;

import java.util.ArrayList;
import java.util.List;

public class OptionsFiltres{
    private List<String> secteur = new ArrayList<>();
    private List<String> taille = new ArrayList<>();
    private List<String> localisation = new ArrayList<>();

    public OptionsFiltres() {}

        public List<String> getSecteurs() { return secteur; }
        public void setSecteurs(List<String> secteur) { this.secteur = secteur; }

        public List<String> getTailles() { return taille; }
        public void setTailles(List<String> taille) { this.taille = taille; }

        public List<String> getLocalisations() { return localisation; }
        public void setLocalisations(List<String> localisation) { this.localisation = localisation; }
}

