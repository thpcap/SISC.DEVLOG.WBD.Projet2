package tutoMicroservices.demo.Application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tutoMicroservices.demo.Infrastucture.EntrepriseRepository; // Adapte l'import au besoin

@Component
public class InitialisationJsonRunner implements CommandLineRunner {

    @Autowired
    private EntrepriseRepository entrepriseRepository;

    @Autowired
    private JsonFiltresService jsonFiltresService;

    @Override
    public void run(String... args) {
        entrepriseRepository.findAll().forEach(entreprise -> {
            jsonFiltresService.mettreAJourFiltres(
                entreprise.getSecteur(),
                entreprise.getTaille(),
                entreprise.getLocalisation()
            );
        });
        System.out.println("-> Fichier base.json synchronisé avec succès !");
    }
}