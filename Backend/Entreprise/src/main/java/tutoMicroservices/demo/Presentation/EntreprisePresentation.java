package tutoMicroservices.demo.Presentation;


import java.util.ArrayList;
import java.util.List;

import jakarta.ws.rs.*;
import org.springframework.beans.factory.annotation.Autowired;

import tutoMicroservices.demo.Application.EmployeDAO;
import tutoMicroservices.demo.Application.Entreprise;
import tutoMicroservices.demo.Application.EntrepriseService;

@Path("entreprises")
public class EntreprisePresentation {
    //permet à Spring de récupérer l'instance EntrepriseService afin de manipuler son contenu
    @Autowired
    private EntrepriseService service;

       @GET 
    @Produces("application/json") 
    public List<EntrepriseDTO> getEntreprises(){
        return mapEntreprises(service.getEntreprises());
    }

    @GET
    @Path("search")
    @Produces("application/json")
    public List<EntrepriseDTO> rechercherEntreprises(
            @QueryParam("secteurs") String secteurs,
            @QueryParam("taille") String taille,
            @QueryParam("ville") String ville) {
        return mapEntreprises(service.rechercherEntreprises(secteurs, taille, ville));
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public EntrepriseDTO getEntrepriseById(@PathParam("id") int id) {
        Entreprise entreprise = service.getEntrepriseById(id)
                .orElseThrow(() -> new NotFoundException("Entreprise introuvable : " + id));
        return mapEntreprises(List.of(entreprise)).get(0);
    }

    private List<EntrepriseDTO> mapEntreprises(List<Entreprise> entreprisesBdd) {
        //ne pas oublier de mapper les données :)  
        EntrepriseMapper em = new EntrepriseMapper(); 
        //on récupère toutes les entreprises  
        //cette liste nous sert d'objet de retour 
        List<EntrepriseDTO> entreprisesRetournees = new ArrayList<>(); 
        //dans cette partie, on transforme les données en EntrepriseDTO 
        for(Entreprise e : entreprisesBdd){ 
            EntrepriseDTO entrepriseAAjouter = new EntrepriseDTO(); 
            entrepriseAAjouter = em.mapEntrepriseToEntrepriseDTO(e); 
            //partie Employes, on va interroger le module Employe  
            //et mettre à dispo les infos employés dans chaque entreprise 
            if(e.getIdEmployes() != null && !e.getIdEmployes().isEmpty()){ 
                //ici onfait l'appel au module Employe via la couche application  
                List<EmployeDAO> employes = service.getEmployes(e.getIdEmployes()); 
          //on map les employés dans l'objet de retour associé et on l'ajoute à son entreprise 
     entrepriseAAjouter.setEmployes(em.mapEmployeDAOToEmployeDTO(employes)); 
            } 
            //on ajoute l'entreprise mappée dans le résultat de la requête 
            entreprisesRetournees.add(entrepriseAAjouter); 
        } 
        return entreprisesRetournees; 
    } 
    //verbe de création
    @POST
    //permet de dire que le webservice attend un json avec la requête
    @Consumes("application/json")
    public void creationEntreprise(CreationEntrepriseDTO entrepriseDTO){
        Entreprise entrepriseToSave = new EntrepriseMapper().mapEntrepriseDTOToEntreprise(entrepriseDTO);
        service.creationEntreprise(entrepriseToSave);
    }
    
}
