package br.reserfy.integrationtests.controller;

import br.reserfy.config.TestConfigs;
import br.reserfy.integrationtests.dto.auth.AuthenticationRequest;
import br.reserfy.integrationtests.dto.auth.AuthenticationResponse;
import br.reserfy.integrationtests.dto.categoria.CategoriaDTO;
import br.reserfy.integrationtests.dto.categoria.CategoriaUpdateDTO;
import br.reserfy.integrationtests.testcontainers.AbstractIntegrationTest;
import br.reserfy.integrationtests.wrappers.categoria.WrapperCategoriaDTO;
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
public class CategoriaControllerTest  extends AbstractIntegrationTest {
    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;
    private static CategoriaDTO categoria;
    private static CategoriaUpdateDTO categoriaUpdate;
    private static AuthenticationResponse authenticationResponse;

    @BeforeAll
    public static void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        categoria = new CategoriaDTO();
        categoriaUpdate = new CategoriaUpdateDTO();
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
                .setBasePath("/api/categoria/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();
    }

    @Test
    @Order(1)
    public void testCreate() throws JsonProcessingException {
        mockCategoria();
        mockCategoriaUpdate();

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .header(TestConfigs.HEADER_PARM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
                .body(categoria)
                .when()
                .post()
                .then()
                .statusCode(201)
                .extract()
                .body()
                .asString();

        CategoriaDTO persistedCategoria = objectMapper.readValue(content, CategoriaDTO.class);
        categoria = persistedCategoria;
        categoriaUpdate.setId(persistedCategoria.getId());

        assertNotNull(persistedCategoria);
        assertNotNull(persistedCategoria.getId());
        assertNotNull(persistedCategoria.getQualificacao());
        assertNotNull(persistedCategoria.getDescricao());
        assertNotNull(persistedCategoria.getUrlImagem());

        assertEquals(persistedCategoria.getId(), categoria.getId());
        assertEquals("Teste", persistedCategoria.getQualificacao());
        assertEquals("Teste", persistedCategoria.getDescricao());
        assertEquals("https://teste.com", persistedCategoria.getUrlImagem());
    }

    @Test
    @Order(2)
    public void testUpdate() throws JsonProcessingException {
        categoriaUpdate.setDescricao("Teste Modificado");
        categoriaUpdate.setQualificacao("Teste Modificado");
        categoriaUpdate.setUrlImagem("https://testemodificado.com");
        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .header(TestConfigs.HEADER_PARM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
                .body(categoriaUpdate)
                .when()
                .post()
                .then()
                .statusCode(201)
                .extract()
                .body()
                .asString();

        CategoriaUpdateDTO persistedCategoria = objectMapper.readValue(content, CategoriaUpdateDTO.class);
        categoriaUpdate = persistedCategoria;

        assertNotNull(persistedCategoria);
        assertNotNull(persistedCategoria.getId());
        assertNotNull(persistedCategoria.getQualificacao());
        assertNotNull(persistedCategoria.getDescricao());
        assertNotNull(persistedCategoria.getUrlImagem());

        assertEquals(persistedCategoria.getId(), categoriaUpdate.getId());
        assertEquals("Teste Modificado", persistedCategoria.getQualificacao());
        assertEquals("Teste Modificado", persistedCategoria.getDescricao());
        assertEquals("https://testemodificado.com", persistedCategoria.getUrlImagem());
    }

    @Test
    @Order(3)
    public void findById() throws JsonProcessingException {
        mockCategoriaUpdate();

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .header(TestConfigs.HEADER_PARM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
                .pathParam("id", categoriaUpdate.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();
        CategoriaDTO persistedCategoria = objectMapper.readValue(content, CategoriaDTO.class);
        categoria = persistedCategoria;

        assertNotNull(persistedCategoria);
        assertNotNull(persistedCategoria.getId());
        assertNotNull(persistedCategoria.getQualificacao());
        assertNotNull(persistedCategoria.getDescricao());
        assertNotNull(persistedCategoria.getUrlImagem());

        assertEquals(persistedCategoria.getId(), categoriaUpdate.getId());
        assertEquals("Teste Modificado", persistedCategoria.getQualificacao());
        assertEquals("Teste Modificado", persistedCategoria.getDescricao());
        assertEquals("https://testemodificado.com", persistedCategoria.getUrlImagem());
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
        WrapperCategoriaDTO wrapper = objectMapper.readValue(content, WrapperCategoriaDTO.class);
        var categorias = wrapper.getEmbeded().getUsers();
        var categoriaFour = categorias.get(3);

        assertNotNull(categoriaFour);
        assertNotNull(categoriaFour.getId());
        assertNotNull(categoriaFour.getDescricao());
        assertNotNull(categoriaFour.getQualificacao());
        assertNotNull(categoriaFour.getUrlImagem());

        assertEquals(categoriaUpdate.getId(), categoriaFour.getId());
        assertEquals("Teste Modificado", categoriaFour.getQualificacao());
        assertEquals("Teste Modificado", categoriaFour.getDescricao());
        assertEquals("https://testemodificado.com", categoriaFour.getUrlImagem());
    }

    @Test
    @Order(6)
    public void testFindAllWithoutToken() {
        RequestSpecification specificationWithoutToken = new RequestSpecBuilder()
                .setBasePath("/api/categoria/v1")
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


        assertTrue(content.contains("{\"first\":{\"href\":\"http://localhost:8080/api/categoria/v1?direction=asc&page=0&size=1&sort=qualificacao,asc\"}"));
        assertTrue(content.contains("\"self\":{\"href\":\"http://localhost:8080/api/categoria/v1?page=0&size=1&direction=asc\"}"));
        assertTrue(content.contains("\"next\":{\"href\":\"http://localhost:8080/api/categoria/v1?direction=asc&page=1&size=1&sort=qualificacao,asc\"}"));
        assertTrue(content.contains("\"last\":{\"href\":\"http://localhost:8080/api/categoria/v1?direction=asc&page=3&size=1&sort=qualificacao,asc\"}}"));

        assertTrue(content.contains("\"page\":{\"size\":1,\"totalElements\":4,\"totalPages\":4,\"number\":0}}"));
    }


    private void mockCategoria() {
        categoria.setDescricao("Teste");
        categoria.setQualificacao("Teste");
        categoria.setUrlImagem("https://teste.com");
    }

    private void mockCategoriaUpdate() {
        categoriaUpdate.setDescricao("Teste");
        categoriaUpdate.setQualificacao("Teste");
        categoriaUpdate.setUrlImagem("https://teste.com");
    }
}
