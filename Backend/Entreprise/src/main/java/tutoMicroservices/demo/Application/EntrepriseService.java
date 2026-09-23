package tutoMicroservices.demo.Application;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tutoMicroservices.demo.Infrastucture.EntrepriseRepository;
import tutoMicroservices.demo.Presentation.FiltresEntrepriseDTO;

// Permet à Spring d'initialiser cet objet et de le configurer
@Service
public class EntrepriseService {

    // Permet de récupérer l'instance de EntrepriseRepository dans Spring
    @Autowired
    private EntrepriseRepository repo;

    public List<Entreprise> getEntreprises() {
        return repo.findAll();
    }

    public Optional<Entreprise> getEntrepriseById(int id) {
        return repo.findById(id);
    }

    public List<Entreprise> rechercherEntreprises(String secteurs, String taille, String ville) {
        String secteurRecherche = normaliser(secteurs);
        String tailleRecherche = normaliser(taille);
        String villeRecherche = normaliser(ville);

        return repo.findAll().stream()
                .filter(entreprise -> secteurRecherche == null
                        || correspond(entreprise.getSecteur(), secteurRecherche))
                .filter(entreprise -> tailleRecherche == null
                        || (entreprise.getTaille() != null
                        && correspond(entreprise.getTaille().name(), tailleRecherche)))
                .filter(entreprise -> villeRecherche == null
                        || correspond(entreprise.getLocalisation(), villeRecherche))
                .collect(Collectors.toList());
    }

    private String normaliser(String valeur) {
        if (valeur == null || valeur.isBlank()) {
            return null;
        }
        return valeur.trim().toLowerCase(Locale.ROOT);
    }

    private boolean correspond(String valeurEntreprise, String valeurRecherche) {
        return valeurEntreprise != null
                && valeurEntreprise.toLowerCase(Locale.ROOT).equals(valeurRecherche);
    }

    public List<EmployeDAO> getEmployes(List<Integer> idEmployes) {
        Client client = ClientBuilder.newClient();

        try {
            WebTarget target = client.target("http://Employe:8081/api/employes");

            for (Integer id : idEmployes) {
                target = target.queryParam("idEmployes", id);
            }

            try (Response response = target.request(MediaType.APPLICATION_JSON).get()) {
                if (response.getStatusInfo().getFamily()
                        != Response.Status.Family.SUCCESSFUL) {
                    throw new IllegalStateException(
                            "Erreur du microservice Employe : HTTP " + response.getStatus());
                }

                return response.readEntity(new GenericType<List<EmployeDAO>>() {});
            }
        } finally {
            client.close();
        }
    }

    public void creationEntreprise(Entreprise entreprise) {
        repo.save(entreprise);
    }

    public FiltresEntrepriseDTO getFiltres() {
        List<String> secteurs = repo.findDistinctSecteurs();
        List<String> localisations = repo.findDistinctLocalisations();

        // Récupération des tailles sous forme de String à partir de l'Enum ou du Repository
        List<String> tailles = repo.findDistinctTailles().stream()
                .map(Object::toString)
                .collect(Collectors.toList());

        return new FiltresEntrepriseDTO(secteurs, localisations, tailles);
    }
}