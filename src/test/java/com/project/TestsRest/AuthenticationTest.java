package com.project.TestsRest;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import com.project.dto.AuthenticationDTO;
import com.project.dto.RegisterUserDTO;
import com.project.enums.Role;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AuthenticationTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void contextLoad() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
        RestAssured.basePath = "/projectvet";
    }

    @Test
    public void testRegisterClientSuccess() {
        RegisterUserDTO client = new RegisterUserDTO(

                "John Doe",
                "test_johndoe@example.com",
                "password123",
                "12345678901",
                Role.CLIENT,
                "123 Main St",
                "1234567890");

        given().contentType(ContentType.JSON).body(client).when().post("/register/client").then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void testRegisterClientEmailAlreadyExists() {
        RegisterUserDTO client = new RegisterUserDTO(
                "John Doe",
                "test_johndoe@example.com",
                "password123",
                "12345678901",
                Role.CLIENT,
                "123 Main St",
                "1234567890");

        given().contentType(ContentType.JSON).body(client).when().post("/register/client").then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void testLoginSuccess() {
        AuthenticationDTO loginData = new AuthenticationDTO("test_johndoe@example.com", "password123");

        Response response = given()
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

        // Extrair o token do corpo da resposta
        String token = response.jsonPath().getString("token");

        // Imprimir o token no terminal
        System.out.println("Token: " + token);

    }

    @Test
    public void testLoginInvalidCredentials() {
        AuthenticationDTO loginData = new AuthenticationDTO("invalid@example.com", "wrongpassword");

        given()
                .contentType(ContentType.JSON)
                .body(loginData)
                .when()
                .post("/login")
                .then()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

}
