package tutoMicroservices.demo.Presentation;


import tutoMicroservices.demo.Application.Entreprise;
import tutoMicroservices.demo.Application.EmployeDAO;

import java.util.ArrayList;
import java.util.List;

public class EntrepriseMapper {

    public EntrepriseDTO mapEntrepriseToEntrepriseDTO(Entreprise entreprise){
        return new EntrepriseDTO(entreprise.getId(), entreprise.getNom(), entreprise.getSecteur(),
                entreprise.getLocalisation(), entreprise.getTaille());
    }
    public Entreprise mapEntrepriseDTOToEntreprise(CreationEntrepriseDTO entrepriseDTO){
        return new Entreprise(entrepriseDTO.getId(), entrepriseDTO.getNom(), entrepriseDTO.getIdEmployes(),
                entrepriseDTO.getSecteur(), entrepriseDTO.getLocalisation(), entrepriseDTO.getTaille());
    }

    public List<EmployeDTO> mapEmployeDAOToEmployeDTO(List<EmployeDAO> employes) {
        List<EmployeDTO> employesDTO = new ArrayList<>();

        for (EmployeDAO employe : employes) {
            employesDTO.add(new EmployeDTO(employe.getId(), employe.getNom()));
        }

        return employesDTO;
    }

}
