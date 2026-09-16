package tutoMicroservices.demo.Infrastucture;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import tutoMicroservices.demo.Application.Entreprise;

//permet à Spring de créer l'objet et de le configurer correctement
@Component
//ici on indique à JPARepository qu'on manipule des objets Entreprise
//et ces derniers possèdent un identifiant en format entier
public interface EntrepriseRepository extends JpaRepository<Entreprise, Integer>{
}