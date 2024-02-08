package br.reserfy.integrationtests.controller;

import br.reserfy.config.TestConfigs;
import br.reserfy.integrationtests.dto.auth.AuthenticationRequest;
import br.reserfy.integrationtests.dto.auth.AuthenticationResponse;
import br.reserfy.integrationtests.dto.user.UserDTO;
import br.reserfy.integrationtests.dto.user.UserUpdateDTO;
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
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerTest { // extends AbstractIntegrationTest
    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;
    private static UserDTO user;
    private static UserUpdateDTO userUpdate;
    private static AuthenticationResponse authenticationResponse;
    private static final String ID = "1a2b3c4d-5e6f-7g8h-9i0j-1k2l3m4n5o6";

    @BeforeAll
    public static void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        user = new UserDTO();
        userUpdate = new UserUpdateDTO();
        authenticationResponse = new AuthenticationResponse();
    }

    @Test
    @Order(0)
    public void authorization() throws JsonProcessingException {
        AuthenticationRequest user = new AuthenticationRequest("admin@gmail.com", "admin123");

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
    public void findById() throws JsonProcessingException {
        mockUser();

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .header(TestConfigs.HEADER_PARM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
                .pathParam("id", user.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();
        System.out.println("Conteudo:" + content);
        UserDTO persistedUser = objectMapper.readValue(content, UserDTO.class);
        user = persistedUser;

        assertNotNull(persistedUser);
        assertNotNull(persistedUser.getId());
        assertNotNull(persistedUser.getFirstname());
        assertNotNull(persistedUser.getLastname());
        assertNotNull(persistedUser.getEmail());
        assertNotNull(persistedUser.getPermissions());

        assertEquals(persistedUser.getId(), user.getId());
        assertEquals("Administrator", persistedUser.getFirstname());
        assertEquals("System", persistedUser.getLastname());
        assertEquals("admin@gmail.com", persistedUser.getEmail());
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
    public void testUpdate() throws JsonProcessingException {
        mockUserUpdate();
        userUpdate.setId("casd123s-5e6f-7g8h-9i0j-asdas3123as");
        userUpdate.setFirstname("Teste Modificado");

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .header(TestConfigs.HEADER_PARM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
                .body(userUpdate)
                .when()
                .put()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();
        UserDTO persistedUser = objectMapper.readValue(content, UserDTO.class);
        user = persistedUser;

        assertNotNull(persistedUser);
        assertNotNull(persistedUser.getId());
        assertNotNull(persistedUser.getFirstname());
        assertNotNull(persistedUser.getLastname());
        assertNotNull(persistedUser.getEmail());
        assertNotNull(persistedUser.getPermissions());

        assertEquals(persistedUser.getId(), user.getId());
        assertEquals("Teste Modificado", persistedUser.getFirstname());
        assertEquals("System", persistedUser.getLastname());
        assertEquals("admin@gmail.com", persistedUser.getEmail());
    }

    @Test
    @Order(5)
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

        assertNotNull(userTwo);
        assertNotNull(userTwo.getId());
        assertNotNull(userTwo.getFirstname());
        assertNotNull(userTwo.getLastname());
        assertNotNull(userTwo.getEmail());
        assertNotNull(userTwo.getPermissions());

        assertEquals(userUpdate.getId(), userTwo.getId());
        assertEquals("Teste Modificado", userTwo.getFirstname());
    }

    @Test
    @Order(6)
    public void testFindAllWithoutToken() {
        RequestSpecification specificationWithoutToken = new RequestSpecBuilder()
                .setBasePath("/api/user/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        given().spec(specificationWithoutToken)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .when()
                .get()
                .then()
                .statusCode(403);
    }

    @Test
    @Order(7)
    public void testHATEOAS() {
        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .queryParams("page", 0, "size", 1, "direction", "asc")
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();


        assertTrue(content.contains("{\"first\":{\"href\":\"http://localhost:8080/api/user/v1?direction=asc&page=0&size=1&sort=firstname,asc\"}"));
        assertTrue(content.contains("\"self\":{\"href\":\"http://localhost:8080/api/user/v1?page=0&size=1&direction=asc\"}"));
        assertTrue(content.contains("\"next\":{\"href\":\"http://localhost:8080/api/user/v1?direction=asc&page=1&size=1&sort=firstname,asc\"}"));
        assertTrue(content.contains("\"last\":{\"href\":\"http://localhost:8080/api/user/v1?direction=asc&page=1&size=1&sort=firstname,asc\"}}"));

        assertTrue(content.contains("\"page\":{\"size\":1,\"totalElements\":2,\"totalPages\":2,\"number\":0}}"));
    }


    private void mockUser() {
        user.setId(ID);
        user.setFirstname("Teste");
        user.setLastname("System");
        user.setEmail("teste@gmail.com");
    }

    private void mockUserUpdate() {
        userUpdate.setId(ID);
        userUpdate.setFirstname("Teste");
        userUpdate.setLastname("System");
        userUpdate.setEmail("teste@gmail.com");
    }
}
