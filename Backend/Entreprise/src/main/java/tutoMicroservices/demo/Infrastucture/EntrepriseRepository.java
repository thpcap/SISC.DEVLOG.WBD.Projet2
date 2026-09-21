package tutoMicroservices.demo.Infrastucture;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tutoMicroservices.demo.Application.Entreprise;

// Permet à Spring de reconnaître ce composant comme un Repository JPA
@Repository
public interface EntrepriseRepository extends JpaRepository<Entreprise, Integer> {

    // Requêtes JPQL pour récupérer les listes sans doublons directement en BDD
    @Query("SELECT DISTINCT e.secteur FROM Entreprise e WHERE e.secteur IS NOT NULL")
    List<String> findDistinctSecteurs();

    @Query("SELECT DISTINCT e.localisation FROM Entreprise e WHERE e.localisation IS NOT NULL")
    List<String> findDistinctLocalisations();

    @Query("SELECT DISTINCT e.taille FROM Entreprise e WHERE e.taille IS NOT NULL")
    List<Object> findDistinctTailles();
}