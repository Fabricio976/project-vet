package com.project.TestsRest;

import static org.hamcrest.Matchers.notNullValue;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.project.dto.AuthenticationDTO;
import com.project.dto.RegisterAnimalDTO;
import com.project.enums.ServicePet;

import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class AnimalResgisterTest extends AuthenticationTestBase {

        @Test
        public void testRegisterAnimalSuccess() {
                AuthenticationDTO loginData = new AuthenticationDTO("test_fabricio@example.com", "password123");

                Response loginResponse = given()
                                .contentType(ContentType.JSON)
                                .body(loginData)
                                .when()
                                .post("/login")
                                .then()
                                .statusCode(HttpStatus.OK.value())
                                .body("token", notNullValue())
                                .body("userId", notNullValue())
                                .extract()
                                .response();

                // Extrair o token e o userId do corpo da resposta
                String token = loginResponse.jsonPath().getString("token");
                String responsible = loginResponse.jsonPath().getString("userId");

                // Criar um novo animal
                RegisterAnimalDTO animal = new RegisterAnimalDTO(
                                "Buddy",
                                5,
                                "Golden Retriever",
                                "Dog",
                                responsible,
                                ServicePet.PETCLINIC);

                // Registrar o animal usando o token de autenticação
                given()
                                .auth()
                                .oauth2(token) // Usando o token de autenticação
                                .contentType(ContentType.JSON)
                                .body(animal)
                                .when()
                                .post("/animals/register")
                                .then()
                                .statusCode(HttpStatus.OK.value());
        }
}