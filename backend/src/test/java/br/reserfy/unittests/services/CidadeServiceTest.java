package br.reserfy.unittests.services;

import br.reserfy.domain.dto.cidade.CidadeDTOMapper;
import br.reserfy.domain.dto.cidade.CidadeUpdateDTOMapper;
import br.reserfy.domain.entity.Cidade;
import br.reserfy.integrationtests.testcontainers.AbstractIntegrationTest;
import br.reserfy.repository.ICidadeRepository;
import br.reserfy.service.CidadeService;
import br.reserfy.service.exceptions.ObjectNotFoundException;
import br.reserfy.unittests.mocks.MockCidade;
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
public class CidadeServiceTest extends AbstractIntegrationTest {
    public static final String UUID_MOCK = "7bf808f8-da36-44ea-8fbd-79653a80023e";
    public static final String UUID_MOCK_CREATE = "7bf807f7-da36-55ae-0dqw-95210a80066a";
    MockCidade input;
    @InjectMocks
    private CidadeService service;
    @Mock
    ICidadeRepository repository;
    @Mock
    CidadeDTOMapper mapper;
    @Mock
    CidadeUpdateDTOMapper updateMapper;

    @BeforeEach
    void setUpMocks() {
        input = new MockCidade();
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testFindById() {
        when(repository.findById(UUID_MOCK)).thenReturn(Optional.of(input.mockEntity(UUID_MOCK)));
        when(mapper.apply(any(Cidade.class))).thenReturn(input.mockDTO(UUID_MOCK));

        var result = service.findById(UUID_MOCK);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getNome());
        assertNotNull(result.getEstado());
        assertNotNull(result.getPais());

        assertTrue(result.toString().contains("links: [<http://localhost/api/cidade/v1/7bf808f8-da36-44ea-8fbd-79653a80023e>;rel=\"self\"]"));

        assertEquals(UUID_MOCK, result.getId());
        assertEquals("Rio de Janeiro", result.getNome());
        assertEquals("Rio de Janeiro", result.getEstado());
        assertEquals("Brasil", result.getPais());
    }

    @Test
    void testFindByIdNotFound() {
        assertThrows(ObjectNotFoundException.class, () -> service.findById("7bf808f8-da36-44ea-8fbd-asdasd312das"));
    }

    @Test
    void testCreate() {
        when(repository.save(any(Cidade.class))).thenReturn(input.mockEntity(UUID_MOCK_CREATE));
        when(mapper.apply(any(Cidade.class))).thenReturn(input.mockDTO(UUID_MOCK_CREATE));

        var result = service.add(input.mockDTO(UUID_MOCK_CREATE));
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getNome());
        assertNotNull(result.getEstado());
        assertNotNull(result.getPais());

        assertTrue(result.toString().contains("links: [<http://localhost/api/cidade/v1/7bf807f7-da36-55ae-0dqw-95210a80066a>;rel=\"self\"]"));

        assertEquals(UUID_MOCK_CREATE, result.getId());
        assertEquals("Rio de Janeiro", result.getNome());
        assertEquals("Rio de Janeiro", result.getEstado());
        assertEquals("Brasil", result.getPais());
    }

    @Test
    void testUpdate() {
        when(repository.findById(UUID_MOCK)).thenReturn(Optional.of(input.mockEntity(UUID_MOCK)));
        when(repository.save(any(Cidade.class))).thenReturn(input.mockEntity(UUID_MOCK));
        when(updateMapper.apply(any(Cidade.class))).thenReturn(input.mockDTOUpdated(UUID_MOCK));

        var result = service.update(input.mockDTOUpdated(UUID_MOCK));
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getNome());
        assertNotNull(result.getEstado());
        assertNotNull(result.getPais());

        assertTrue(result.toString().contains("links: [<http://localhost/api/cidade/v1/7bf808f8-da36-44ea-8fbd-79653a80023e>;rel=\"self\"]"));

        assertEquals(UUID_MOCK, result.getId());
        assertEquals("Rio de Janeiro", result.getNome());
        assertEquals("Rio de Janeiro", result.getEstado());
        assertEquals("Brasil", result.getPais());
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
