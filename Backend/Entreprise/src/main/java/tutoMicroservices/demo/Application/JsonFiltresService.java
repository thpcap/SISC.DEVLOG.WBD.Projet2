package tutoMicroservices.demo.Application;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class JsonFiltresService {

    private static final String FICHIER_JSON = "../../Frontend/base.json";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public synchronized void mettreAJourFiltres(String secteur, String taille, String localisation) {
        File file = new File(FICHIER_JSON);
        OptionsFiltres filtres = new OptionsFiltres();

        if (file.exists() && file.length() > 0) {
            try {
                filtres = objectMapper.readValue(file, OptionsFiltres.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        boolean modifie = false;

        if (secteur != null && !contientIgnoreCase(filtres.getSecteurs(), secteur)) {
            filtres.getSecteurs().add(secteur);
            modifie = true;
        }
        if (taille != null && !contientIgnoreCase(filtres.getTailles(), taille)) {
            filtres.getTailles().add(taille);
            modifie = true;
        }
        if (localisation != null && !contientIgnoreCase(filtres.getLocalisations(), localisation)) {
            filtres.getLocalisations().add(localisation);
            modifie = true;
        }

        if (modifie) {
            try {
                objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, filtres);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean contientIgnoreCase(Iterable<String> liste, String valeur) {
        for (String item : liste) {
            if (item.equalsIgnoreCase(valeur)) {
                return true;
            }
        }
        return false;
    }
}