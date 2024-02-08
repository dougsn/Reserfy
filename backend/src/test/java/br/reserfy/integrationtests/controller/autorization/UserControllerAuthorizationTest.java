package br.reserfy.integrationtests.controller.autorization;

import br.reserfy.config.TestConfigs;
import br.reserfy.integrationtests.dto.auth.AuthenticationRequest;
import br.reserfy.integrationtests.dto.auth.AuthenticationResponse;
import br.reserfy.integrationtests.testcontainers.AbstractIntegrationTest;
import br.reserfy.integrationtests.wrappers.user.WrapperUserDTO;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerAuthorizationTest extends AbstractIntegrationTest {
    private static RequestSpecification specification;
    private static RequestSpecification specificationWithoutAuthorization;
    private static ObjectMapper objectMapper;
    private static AuthenticationResponse authenticationResponse;
    private static AuthenticationResponse authenticationResponseWithoutAuthorization;

    @BeforeAll
    public static void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        authenticationResponse = new AuthenticationResponse();
        authenticationResponseWithoutAuthorization = new AuthenticationResponse();
    }

    @Test
    @Order(0)
    public void authorization() throws JsonProcessingException {
        AuthenticationRequest user = new AuthenticationRequest("Administrator", "admin123");

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
    }

    @Test
    @Order(1)
    public void authorizationWithoutAuthorization() throws JsonProcessingException {
        AuthenticationRequest user = new AuthenticationRequest("Common User", "admin123");

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

        authenticationResponseWithoutAuthorization = objectMapper.readValue(token, AuthenticationResponse.class);
        specificationWithoutAuthorization = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARM_AUTHORIZATION, "Bearer " + authenticationResponseWithoutAuthorization.getToken())
                .setBasePath("/api/user/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();
    }

    @Test
    @Order(2)
    public void testDelete() {
        given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .pathParam("id", "casd123s-5e6f-7g8h-9i0j-1k2l3m4n5o6")
                .when()
                .delete("{id}")
                .then()
                .statusCode(204);
    }

    @Test
    @Order(3)
    public void testDeleteWithoutAuthorization() {
        given().spec(specificationWithoutAuthorization)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .pathParam("id", "casd123s-5e6f-7g8h-9i0j-asdas3123as")
                .when()
                .delete("{id}")
                .then()
                .statusCode(403);
    }

    @Test
    @Order(4)
    public void testFindAll() throws JsonProcessingException {
        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .queryParams("page", 0, "size", 5, "direction", "asc")
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();
        WrapperUserDTO wrapper = objectMapper.readValue(content, WrapperUserDTO.class);
        var users = wrapper.getEmbeded().getUsers();
        var userTwo = users.get(1);
        System.out.println(userTwo);

        assertNotNull(userTwo);
        assertNotNull(userTwo.getId());
        assertNotNull(userTwo.getFirstname());
        assertNotNull(userTwo.getLastname());
        assertNotNull(userTwo.getEmail());
        assertNotNull(userTwo.getPermissions());

        assertEquals("casd123s-5e6f-7g8h-9i0j-asdas3123as", userTwo.getId());
        assertEquals("Common", userTwo.getFirstname());
    }

    @Test
    @Order(5)
    public void testFindAllWithoutAuthorization() throws JsonProcessingException {
        given().spec(specificationWithoutAuthorization)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .queryParams("page", 0, "size", 5, "direction", "asc")
                .when()
                .get()
                .then()
                .statusCode(403);
    }
}
