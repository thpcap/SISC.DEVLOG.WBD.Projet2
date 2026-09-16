package tutoMicroservices.demo.Application;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tutoMicroservices.demo.Infrastucture.EmployeRepository;

//permet à Spring d'initialiser cet objet et de le configurer
@Service
public class EmployeService {

    //permet de récupérer l'instance de EmployeRepository dans Spring et de manipuler JPA à travers cette dernière
    @Autowired
    private EmployeRepository repo;

    public List<Employe> getEmploye(){
        return repo.findAll();
        //retourne toutes les Employes contenues en bdd
    }
    public void creationEmploye(Employe employe){
        repo.save(employe);
    }

    public List<Employe> getEmployesByIds(List<Integer> idEmployes){
        List<Employe> employesRetournes = new ArrayList<>();

        //parcoure tous les identifiants reçus et va chercher les infos employés unpar un
        //ce n'est pas la méthode la plus optimisée mais c'est une solution simple
        for(int id : idEmployes){
            Optional<Employe> e = repo.findById(id);
            //ce controle permet d'ajouter les infos employé seulement si elles existent
            if(e.isPresent()){
                employesRetournes.add(e.get());
            }
        }
        return employesRetournes;
    }
}