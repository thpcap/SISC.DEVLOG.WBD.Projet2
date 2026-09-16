package tutoMicroservices.demo.Application;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Service
public class EmployeService {

    public List<EmployeDAO> getEmployes(List<Integer> idEmployes) {
        Client client = ClientBuilder.newClient();

        try {
            WebTarget target =
                    client.target("http://localhost:8081/api/employes");

            for (Integer id : idEmployes) {
                target = target.queryParam("idEmployes", id);
            }

            try (Response response =
                         target.request(MediaType.APPLICATION_JSON).get()) {

                if (response.getStatusInfo().getFamily()
                        != Response.Status.Family.SUCCESSFUL) {
                    throw new IllegalStateException(
                            "Erreur du microservice Employé : HTTP "
                                    + response.getStatus()
                    );
                }

                return response.readEntity(
                        new GenericType<List<EmployeDAO>>() {}
                );
            }
        } finally {
            client.close();
        }
    }
}