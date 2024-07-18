package com.project.TestsRest;

import org.junit.jupiter.api.Test;

import com.project.dto.RegisterAnimalDTO;
import com.project.entitys.Usuario;
import com.project.enums.Role;
import com.project.enums.ServicePet;

public class AnimalResgisterTest extends AuthenticationTestBase{

    @Test
    public void testRegisterAnimalSuccess() {
        // Cria um usuário responsável e um serviço de exemplo
        Usuario responsible = new Usuario("Responsible User", "responsible@example.com", "password123", "12345678901", Role.CLIENT, "456 Another St", "0987654321");

        // Cria o DTO de registro de animal
        RegisterAnimalDTO animal = new RegisterAnimalDTO(
                "Buddy",
                5,
                "Golden Retriever",
                "Dog",
                responsible,
               ServicePet.PETCLINIC);

    //     given()
    //             .header("Authorization", "Bearer " +to)
    //             .contentType(ContentType.JSON)
    //             .body(animal)
    //             .when()
    //             .post("/register/animal")
    //             .then()
    //             .statusCode(HttpStatus.OK.value());
    }
}

