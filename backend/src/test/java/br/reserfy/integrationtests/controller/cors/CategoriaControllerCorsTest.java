package br.reserfy.integrationtests.controller.cors;

import br.reserfy.config.TestConfigs;
import br.reserfy.integrationtests.dto.auth.AuthenticationRequest;
import br.reserfy.integrationtests.dto.auth.AuthenticationResponse;
import br.reserfy.integrationtests.dto.categoria.CategoriaDTO;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoriaControllerCorsTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;
    private static CategoriaDTO categoria;
    private static AuthenticationResponse authenticationResponse;
    private static final String ID = "1a2b3c4d-5e6f-7g8h-9i0j-1k2l3m4n5o6";

    @BeforeAll
    public static void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        categoria = new CategoriaDTO();
        authenticationResponse = new AuthenticationResponse();
    }


    @Test
    @Order(0)
    public void authorization() throws JsonProcessingException {
        AuthenticationRequest categoria = new AuthenticationRequest("admin@gmail.com", "admin123");

        var token = given()
                .basePath("/api/auth/v1/login")
                .port(TestConfigs.SERVER_PORT)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(categoria)
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
    public void findById() throws JsonProcessingException {
        mockCategoria();

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .header(TestConfigs.HEADER_PARM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
                .pathParams("id", categoria.getId())
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

        assertEquals(persistedCategoria.getId(), categoria.getId());
        assertEquals("5 Estrelas", persistedCategoria.getQualificacao());
        assertEquals("Hotel 5 Estrelas", persistedCategoria.getDescricao());
        assertEquals("https://teste5.com", persistedCategoria.getUrlImagem());
    }

    @Test
    @Order(4)
    public void findByIdWithWrongOrigin() {
        mockCategoria();

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .header(TestConfigs.HEADER_PARM_ORIGIN, TestConfigs.ORIGIN_FAIL)
                .pathParams("id", categoria.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(403)
                .extract()
                .body()
                .asString();


        assertNotNull(content);
        assertEquals("Invalid CORS request", content);
    }

    private void mockCategoria() {
        categoria.setId(ID);
        categoria.setDescricao("Teste");
        categoria.setQualificacao("Teste");
        categoria.setUrlImagem("https://teste.com");
    }
}
