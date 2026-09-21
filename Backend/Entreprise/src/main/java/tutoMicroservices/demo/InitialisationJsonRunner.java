package tutoMicroservices.demo;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map; // Add this import
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional; // Adapte l'import si besoin[cite: 1]

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import tutoMicroservices.demo.Application.Entreprise;
import tutoMicroservices.demo.Infrastucture.EntrepriseRepository;

@Component
@Profile("!test") // Ne s'exécute pas lors des tests unitaires JUnit
public class InitialisationJsonRunner implements CommandLineRunner {

    @Autowired
    private EntrepriseRepository entrepriseRepository;

    @Override
    @Transactional
    public void run(String... args) {
        try {
            List<Entreprise> entreprises = entrepriseRepository.findAll();

            Set<String> secteursUniques = new HashSet<>();
            Set<String> localisationsUniques = new HashSet<>();
            Set<String> taillesUniques = new HashSet<>();

            for (Entreprise e : entreprises) {
                if (e.getSecteur() != null) {
                    secteursUniques.add(e.getSecteur());
                }
                if (e.getLocalisation() != null) {
                    localisationsUniques.add(e.getLocalisation());
                }
                if (e.getTaille() != null) {
                    taillesUniques.add(e.getTaille().name());
                }
            }

            Map<String, Object> filtresJson = new HashMap<>();
            filtresJson.put("secteurs", secteursUniques);
            filtresJson.put("localisations", localisationsUniques);
            filtresJson.put("tailles", taillesUniques);

            File file = new File("src/main/resources/entreprises.json");
            if (file.getParentFile() != null && !file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, filtresJson);

            System.out.println("--> Fichier entreprises.json généré avec succès !");
        } catch (Exception e) {
            System.err.println("--> Avertissement : Erreur lors de la création du JSON : " + e.getMessage());
        }
    }
}