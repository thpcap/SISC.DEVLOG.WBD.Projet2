package tutoMicroservices.demo.Infrastucture;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import tutoMicroservices.demo.Application.Entreprise;

@Component
public class JsonFileWriter {

    private static EntrepriseRepository repository;

    @Autowired
    public void setRepository(EntrepriseRepository repo) {
        JsonFileWriter.repository = repo;
    }

    public static void exporterJson() {
        if (repository == null) return;

        try {
            // Récupère les données et copie dans une liste standard pour détacher de JPA
            List<Entreprise> entreprises = new ArrayList<>(repository.findAll());

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

            File file = new File("src/main/resources/entreprises.json");
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, entreprises);

            System.out.println("--> Fichier entreprises.json généré avec " + entreprises.size() + " entreprise(s) !");
        } catch (IOException e) {
            System.err.println("Erreur d'écriture JSON : " + e.getMessage());
        }
    }
}
