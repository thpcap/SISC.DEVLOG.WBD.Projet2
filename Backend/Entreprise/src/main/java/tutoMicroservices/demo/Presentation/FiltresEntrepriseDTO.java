package  tutoMicroservices.demo.Presentation;

import java.util.List;

public class FiltresEntrepriseDTO {
    private List<String> secteurs;
    private List<String> localisations;
    private List<String> tailles;

    public FiltresEntrepriseDTO() {}

    public FiltresEntrepriseDTO(List<String> secteurs, List<String> localisations, List<String> tailles) {
        this.secteurs = secteurs;
        this.localisations = localisations;
        this.tailles = tailles;
    }

    public List<String> getSecteurs() { return secteurs; }
    public void setSecteurs(List<String> secteurs) { this.secteurs = secteurs; }

    public List<String> getLocalisations() { return localisations; }
    public void setLocalisations(List<String> localisations) { this.localisations = localisations; }

    public List<String> getTailles() { return tailles; }
    public void setTailles(List<String> tailles) { this.tailles = tailles; }
}