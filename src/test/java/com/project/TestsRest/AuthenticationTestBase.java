package com.project.TestsRest;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.hamcrest.Matchers.notNullValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import com.project.dto.AuthenticationDTO;
import com.project.dto.RegisterUserDTO;
import com.project.enums.Role;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AuthenticationTestBase {

    @LocalServerPort
    private int port;
    private final static AtomicBoolean isClientRegistered = new AtomicBoolean(false);

    @BeforeEach
    void contextLoad() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
        RestAssured.basePath = "/projectvet";
    }

    @Test
    public void testRegisterClientSuccess() {
        if (isClientRegistered.compareAndSet(false, true)) {
            RegisterUserDTO client = new RegisterUserDTO(

                    "Fabricio Felizardo",
                    "test_fabricio@example.com",
                    "password123",
                    "12345678901",
                    Role.CLIENT,
                    "123 Main St",
                    "1234567890");

            given().contentType(ContentType.JSON).body(client).when().post("/register/client").then()
                    .statusCode(HttpStatus.OK.value());
        }
    }

    @Test
    public void testRegisterManagerSuccess() {
        if (isClientRegistered.compareAndSet(false, true)) {
            RegisterUserDTO manager = new RegisterUserDTO(

                    "Maria Doe",
                    "test_MariaDoe@example.com",
                    "password123",
                    "32145678901",
                    Role.MANAGER,
                    "123 Main St",
                    "4324567890");

            given().contentType(ContentType.JSON).body(manager).when().post("/register/funcionario").then()
                    .statusCode(HttpStatus.OK.value());
        }
    }

    @Test
    public void testRegisterClientEmailAlreadyExists() {
        RegisterUserDTO client = new RegisterUserDTO(
                "Fabricio Felizardo",
                "test_fabricio@example.com",
                "password123",
                "12345678901",
                Role.CLIENT,
                "123 Main St",
                "1234567890");

        given()
                .contentType(ContentType.JSON)
                .body(client)
                .when()
                .post("/register/client").then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void testLoginSuccess() {
        AuthenticationDTO loginData = new AuthenticationDTO("test_fabricio@example.com", "password123");

        given()
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
