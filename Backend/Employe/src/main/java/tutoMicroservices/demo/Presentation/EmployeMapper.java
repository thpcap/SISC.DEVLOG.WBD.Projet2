package tutoMicroservices.demo.Presentation;


import tutoMicroservices.demo.Application.Employe;

public class EmployeMapper {

    public EmployeDTO mapEmployeToEmployeDTO(Employe employe){
        return new EmployeDTO(employe.getId(), employe.getNom(), employe.getPrenom(),
                employe.getDateArrivee());
    }
    public Employe mapEmployeDTOToEmploye(CreationEmployeDTO entrepriseDTO){
        return new Employe(entrepriseDTO.getId(), entrepriseDTO.getNom(), entrepriseDTO.getPrenom(),
                entrepriseDTO.getDateArrivee());
    }
}
