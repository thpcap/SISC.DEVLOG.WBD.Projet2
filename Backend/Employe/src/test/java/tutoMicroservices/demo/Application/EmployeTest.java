package tutoMicroservices.demo.Application;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import tutoMicroservices.demo.Application.Employe;


public class EmployeTest {
    @Test
    void testGetPrenom() {
        // Arrange
        String prenom = "Test";
        Employe employe = new Employe(0,null, prenom,null);

        // Act
        String resultat = employe.getPrenom();

        // Assert
        assertEquals(prenom, resultat);
    }
}
