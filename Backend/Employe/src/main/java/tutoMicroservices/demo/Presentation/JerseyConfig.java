package tutoMicroservices.demo.Presentation;

import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

//permet de configurer l'instanciation avec Spring
@Component

//donne un chemin de base aux liens qui vont être exposés
@ApplicationPath("api")

//indique à Spring que le composant qui va être créé qu'il s'agit d'une configuration globale
@Configuration
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig(){
        //enregistre la classe où les webservices vont être exposés
        register(EmployePresentation.class);
        register(CorsFilter.class);

    }
}