package br.reserfy.integrationtests.controller;

import br.reserfy.config.TestConfigs;
import br.reserfy.integrationtests.dto.auth.AuthenticationRequest;
import br.reserfy.integrationtests.dto.auth.AuthenticationResponse;
import br.reserfy.integrationtests.dto.auth.RegisterRequest;
import br.reserfy.integrationtests.dto.user.UserDTO;
import br.reserfy.integrationtests.dto.user.UserInfoDTO;
import br.reserfy.integrationtests.testcontainers.AbstractIntegrationTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthControllerTest extends AbstractIntegrationTest {
    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;
    private static UserDTO user;
    private static AuthenticationResponse authenticationResponse;
    private static String ID_NEW_USER;

    @BeforeAll
    public static void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        user = new UserDTO();
        authenticationResponse = new AuthenticationResponse();
    }

    @Test
    @Order(0)
    public void testRegister() throws JsonProcessingException {
        RegisterRequest newUser = new RegisterRequest("teste@gmail.com", "admin123");

        var token = given()
                .basePath("/api/auth/v1/register")
                .port(TestConfigs.SERVER_PORT)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(newUser)
                .when()
                .post()
                .then()
                .statusCode(201)
                .extract()
                .body()
                .asString();

        authenticationResponse = objectMapper.readValue(token, AuthenticationResponse.class);

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARM_AUTHORIZATION, "Bearer " + authenticationResponse.getToken())
                .setBasePath("/api/auth/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        assertNotNull(token);
    }

    @Test
    @Order(1)
    public void testLogin() {
        AuthenticationRequest user = new AuthenticationRequest("teste@gmail.com", "admin123");

        var token = given().spec(specification)
                .basePath("/api/auth/v1/login")
                .port(TestConfigs.SERVER_PORT)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(user)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        assertNotNull(token);
    }


    @Test
    @Order(2)
    public void testInfoUser() throws JsonProcessingException {
        var userInfo = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .get()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();
        UserInfoDTO info = objectMapper.readValue(userInfo, UserInfoDTO.class);
        ID_NEW_USER = info.getId();
        assertNotNull(userInfo);
    }

    @Test
    @Order(3)
    public void testDeleteUser() throws JsonProcessingException {
        AuthenticationRequest user = new AuthenticationRequest("teste@gmail.com", "admin123");
        var token = given()
                .basePath("/api/auth/v1/login")
                .port(TestConfigs.SERVER_PORT)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(user)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        authenticationResponse = objectMapper.readValue(token, AuthenticationResponse.class);
        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARM_AUTHORIZATION, "Bearer " + authenticationResponse.getToken())
                .setBasePath("/api/user/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .pathParam("id", ID_NEW_USER)
                .when()
                .delete("{id}")
                .then()
                .statusCode(204);
    }
}