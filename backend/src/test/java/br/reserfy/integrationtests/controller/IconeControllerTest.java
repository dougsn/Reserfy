package br.reserfy.integrationtests.controller;

import br.reserfy.config.TestConfigs;
import br.reserfy.integrationtests.dto.auth.AuthenticationRequest;
import br.reserfy.integrationtests.dto.auth.AuthenticationResponse;
import br.reserfy.integrationtests.dto.icone.IconeDTO;
import br.reserfy.integrationtests.dto.icone.IconeUpdateDTO;
import br.reserfy.integrationtests.testcontainers.AbstractIntegrationTest;
import br.reserfy.integrationtests.wrappers.icone.WrapperIconeDTO;
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
public class IconeControllerTest extends AbstractIntegrationTest {
    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;
    private static IconeDTO icone;
    private static IconeUpdateDTO iconeUpdate;
    private static AuthenticationResponse authenticationResponse;

    @BeforeAll
    public static void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        icone = new IconeDTO();
        iconeUpdate = new IconeUpdateDTO();
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
                .setBasePath("/api/icone/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();
    }

    @Test
    @Order(1)
    public void testCreate() throws JsonProcessingException {
        mockIcone();
        mockIconeUpdate();

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .header(TestConfigs.HEADER_PARM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
                .body(icone)
                .when()
                .post()
                .then()
                .statusCode(201)
                .extract()
                .body()
                .asString();

        IconeDTO persistedIcone = objectMapper.readValue(content, IconeDTO.class);
        icone = persistedIcone;
        iconeUpdate.setId(persistedIcone.getId());

        assertNotNull(persistedIcone);
        assertNotNull(persistedIcone.getId());
        assertNotNull(persistedIcone.getNome());
        assertNotNull(persistedIcone.getNomeIcone());

        assertEquals(persistedIcone.getId(), icone.getId());
        assertEquals("danceteria", persistedIcone.getNome());
        assertEquals("#danceteria", persistedIcone.getNomeIcone());
    }

    @Test
    @Order(2)
    public void testUpdate() throws JsonProcessingException {
        iconeUpdate.setNome("danceteria Modificado");
        iconeUpdate.setNomeIcone("#danceteria Modificado");
        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .header(TestConfigs.HEADER_PARM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
                .body(iconeUpdate)
                .when()
                .post()
                .then()
                .statusCode(201)
                .extract()
                .body()
                .asString();

        IconeUpdateDTO persistedIcone = objectMapper.readValue(content, IconeUpdateDTO.class);
        iconeUpdate = persistedIcone;

        assertNotNull(persistedIcone);
        assertNotNull(persistedIcone.getId());
        assertNotNull(persistedIcone.getNome());
        assertNotNull(persistedIcone.getNomeIcone());

        assertEquals(persistedIcone.getId(), iconeUpdate.getId());
        assertEquals("danceteria Modificado", persistedIcone.getNome());
        assertEquals("#danceteria Modificado", persistedIcone.getNomeIcone());
    }

    @Test
    @Order(3)
    public void findById() throws JsonProcessingException {
        mockIconeUpdate();

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .header(TestConfigs.HEADER_PARM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
                .pathParam("id", iconeUpdate.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();
        IconeDTO persistedIcone = objectMapper.readValue(content, IconeDTO.class);
        icone = persistedIcone;

        assertNotNull(persistedIcone);
        assertNotNull(persistedIcone);
        assertNotNull(persistedIcone.getId());
        assertNotNull(persistedIcone.getNome());
        assertNotNull(persistedIcone.getNomeIcone());

        assertEquals(persistedIcone.getId(), icone.getId());
        assertEquals("danceteria Modificado", persistedIcone.getNome());
        assertEquals("#danceteria Modificado", persistedIcone.getNomeIcone());
    }

    @Test
    @Order(4)
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
        WrapperIconeDTO wrapper = objectMapper.readValue(content, WrapperIconeDTO.class);
        var cidades = wrapper.getEmbeded().getIcones();
        var cidadeThree = cidades.get(2);

        assertNotNull(cidadeThree);
        assertNotNull(cidadeThree.getId());
        assertNotNull(cidadeThree.getNome());
        assertNotNull(cidadeThree.getNomeIcone());

        assertEquals(iconeUpdate.getId(), cidadeThree.getId());
        assertEquals("danceteria Modificado", cidadeThree.getNome());
        assertEquals("#danceteria Modificado", cidadeThree.getNomeIcone());
    }

    @Test
    @Order(6)
    public void testFindAllWithoutToken() {
        RequestSpecification specificationWithoutToken = new RequestSpecBuilder()
                .setBasePath("/api/icone/v1")
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


        assertTrue(content.contains("{\"first\":{\"href\":\"http://localhost:8080/api/icone/v1?direction=asc&page=0&size=1&sort=nome,asc\"}"));
        assertTrue(content.contains("\"self\":{\"href\":\"http://localhost:8080/api/icone/v1?page=0&size=1&direction=asc\"}"));
        assertTrue(content.contains("\"next\":{\"href\":\"http://localhost:8080/api/icone/v1?direction=asc&page=1&size=1&sort=nome,asc\"}"));
        assertTrue(content.contains("\"last\":{\"href\":\"http://localhost:8080/api/icone/v1?direction=asc&page=3&size=1&sort=nome,asc\"}}"));

        assertTrue(content.contains("\"page\":{\"size\":1,\"totalElements\":4,\"totalPages\":4,\"number\":0}}"));
    }


    private void mockIcone() {
        icone.setNome("danceteria");
        icone.setNomeIcone("#danceteria");
    }

    private void mockIconeUpdate() {
        iconeUpdate.setNome("danceteria");
        iconeUpdate.setNomeIcone("#danceteria");
    }
}
