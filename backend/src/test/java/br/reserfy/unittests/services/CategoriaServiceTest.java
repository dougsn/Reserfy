package br.reserfy.unittests.services;

import br.reserfy.domain.dto.categoria.CategoriaDTOMapper;
import br.reserfy.domain.dto.categoria.CategoriaUpdateDTOMapper;
import br.reserfy.domain.entity.Categoria;
import br.reserfy.integrationtests.testcontainers.AbstractIntegrationTest;
import br.reserfy.repository.ICategoriaRepository;
import br.reserfy.service.CategoriaService;
import br.reserfy.service.exceptions.ObjectNotFoundException;
import br.reserfy.unittests.mocks.MockCategoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class CategoriaServiceTest extends AbstractIntegrationTest {
    public static final String UUID_MOCK = "7bf808f8-da36-44ea-8fbd-79653a80023e";
    public static final String UUID_MOCK_CREATE = "7bf807f7-da36-55ae-0dqw-95210a80066a";
    MockCategoria input;
    @InjectMocks
    private CategoriaService service;
    @Mock
    ICategoriaRepository repository;
    @Mock
    CategoriaDTOMapper mapper;
    @Mock
    CategoriaUpdateDTOMapper updateMapper;

    @BeforeEach
    void setUpMocks() {
        input = new MockCategoria();
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testFindById() {
        when(repository.findById(UUID_MOCK)).thenReturn(Optional.of(input.mockEntity(UUID_MOCK)));
        when(mapper.apply(any(Categoria.class))).thenReturn(input.mockDTO(UUID_MOCK));

        var result = service.findById(UUID_MOCK);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getQualificacao());
        assertNotNull(result.getDescricao());
        assertNotNull(result.getUrlImagem());

        assertTrue(result.toString().contains("links: [<http://localhost/api/categoria/v1/7bf808f8-da36-44ea-8fbd-79653a80023e>;rel=\"self\"]"));

        assertEquals(UUID_MOCK, result.getId());
        assertEquals("Teste", result.getQualificacao());
        assertEquals("Teste", result.getDescricao());
        assertEquals("https://teste.com", result.getUrlImagem());
    }

    @Test
    void testFindByIdNotFound() {
        assertThrows(ObjectNotFoundException.class, () -> service.findById("7bf808f8-da36-44ea-8fbd-asdasd312das"));
    }

    @Test
    void testCreate() {
        when(repository.save(any(Categoria.class))).thenReturn(input.mockEntity(UUID_MOCK_CREATE));
        when(mapper.apply(any(Categoria.class))).thenReturn(input.mockDTO(UUID_MOCK_CREATE));

        var result = service.add(input.mockDTO(UUID_MOCK_CREATE));
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getQualificacao());
        assertNotNull(result.getDescricao());
        assertNotNull(result.getUrlImagem());

        assertTrue(result.toString().contains("links: [<http://localhost/api/categoria/v1/7bf807f7-da36-55ae-0dqw-95210a80066a>;rel=\"self\"]"));

        assertEquals(UUID_MOCK_CREATE, result.getId());
        assertEquals("Teste", result.getQualificacao());
        assertEquals("Teste", result.getDescricao());
        assertEquals("https://teste.com", result.getUrlImagem());
    }

    @Test
    void testUpdate() {
        when(repository.findById(UUID_MOCK)).thenReturn(Optional.of(input.mockEntity(UUID_MOCK)));
        when(repository.save(any(Categoria.class))).thenReturn(input.mockEntity(UUID_MOCK));
        when(updateMapper.apply(any(Categoria.class))).thenReturn(input.mockDTOUpdated(UUID_MOCK));

        var result = service.update(input.mockDTOUpdated(UUID_MOCK));
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getQualificacao());
        assertNotNull(result.getDescricao());
        assertNotNull(result.getUrlImagem());

        assertTrue(result.toString().contains("links: [<http://localhost/api/categoria/v1/7bf808f8-da36-44ea-8fbd-79653a80023e>;rel=\"self\"]"));

        assertEquals(UUID_MOCK, result.getId());
        assertEquals("Teste", result.getQualificacao());
        assertEquals("Teste", result.getDescricao());
        assertEquals("https://teste.com", result.getUrlImagem());
    }

    @Test
    void testDelete() {
        when(repository.findById(UUID_MOCK)).thenReturn(Optional.of(input.mockEntity(UUID_MOCK)));
        service.delete(UUID_MOCK);
    }

    @Test
    void testDeleteNotFound() {
        assertThrows(ObjectNotFoundException.class, () -> service.delete("3dasd1-da36-44ea-8fbd-asdasd312das"));
    }


}
