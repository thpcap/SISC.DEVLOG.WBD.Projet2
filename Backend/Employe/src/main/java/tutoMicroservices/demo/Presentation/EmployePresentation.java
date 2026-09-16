package tutoMicroservices.demo.Presentation;


import java.util.ArrayList;
import java.util.List;

import jakarta.ws.rs.*;
import org.springframework.beans.factory.annotation.Autowired;

import tutoMicroservices.demo.Application.Employe;
import tutoMicroservices.demo.Application.EmployeService;

@Path("employes")
public class EmployePresentation {
    //permet à Spring de récupérer l'instance EmployeService afin de manipuler son contenu
    @Autowired
    private EmployeService service;

    @GET
    @Produces("application/json")
    public List<EmployeDTO> getEmployes(@QueryParam("idEmployes") List<Integer>
            idEmployes){
        //ne pas oublier de mapper les données :)

        EmployeMapper em = new EmployeMapper();
        List<Employe> employesBdd = new ArrayList<>();
        if(idEmployes.isEmpty()){
            employesBdd = service.getEmploye();
        }else{
            employesBdd = service.getEmployesByIds(idEmployes);
        }

        List<EmployeDTO> employesRetournees = new ArrayList<>();
        //dans cette partie, on transforme les données en EntrepriseDTO
        for(Employe e : employesBdd){
            employesRetournees.add(em.mapEmployeToEmployeDTO(e));
        }
        return employesRetournees;
    }
    //verbe de création
    @POST
    //permet de dire que le webservice attend un json avec la requête
    @Consumes("application/json")
    public void creationEmploye(CreationEmployeDTO employeDTO){
        Employe employeToSave = new EmployeMapper().mapEmployeDTOToEmploye(employeDTO);
        service.creationEmploye(employeToSave);
    }

}