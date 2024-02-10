package br.reserfy.unittests.services;

import br.reserfy.domain.dto.icone.IconeDTOMapper;
import br.reserfy.domain.dto.icone.IconeUpdateDTOMapper;
import br.reserfy.domain.entity.Icone;
import br.reserfy.integrationtests.testcontainers.AbstractIntegrationTest;
import br.reserfy.repository.IIconeRepository;
import br.reserfy.service.IconeService;
import br.reserfy.service.exceptions.ObjectNotFoundException;
import br.reserfy.unittests.mocks.MockIcone;
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
public class IconeServiceTest extends AbstractIntegrationTest {
    public static final String UUID_MOCK = "7bf808f8-da36-44ea-8fbd-79653a80023e";
    public static final String UUID_MOCK_CREATE = "7bf807f7-da36-55ae-0dqw-95210a80066a";
    MockIcone input;
    @InjectMocks
    private IconeService service;
    @Mock
    IIconeRepository repository;
    @Mock
    IconeDTOMapper mapper;
    @Mock
    IconeUpdateDTOMapper updateMapper;

    @BeforeEach
    void setUpMocks() {
        input = new MockIcone();
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testFindById() {
        when(repository.findById(UUID_MOCK)).thenReturn(Optional.of(input.mockEntity(UUID_MOCK)));
        when(mapper.apply(any(Icone.class))).thenReturn(input.mockDTO(UUID_MOCK));

        var result = service.findById(UUID_MOCK);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getNome());
        assertNotNull(result.getNomeIcone());

        assertTrue(result.toString().contains("links: [<http://localhost/api/icone/v1/7bf808f8-da36-44ea-8fbd-79653a80023e>;rel=\"self\"]"));

        assertEquals(UUID_MOCK, result.getId());
        assertEquals("salao", result.getNome());
        assertEquals("#salao", result.getNomeIcone());
    }

    @Test
    void testFindByIdNotFound() {
        assertThrows(ObjectNotFoundException.class, () -> service.findById("7bf808f8-da36-44ea-8fbd-asdasd312das"));
    }

    @Test
    void testCreate() {
        when(repository.save(any(Icone.class))).thenReturn(input.mockEntity(UUID_MOCK_CREATE));
        when(mapper.apply(any(Icone.class))).thenReturn(input.mockDTO(UUID_MOCK_CREATE));

        var result = service.add(input.mockDTO(UUID_MOCK_CREATE));
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getNome());
        assertNotNull(result.getNomeIcone());

        assertTrue(result.toString().contains("links: [<http://localhost/api/icone/v1/7bf807f7-da36-55ae-0dqw-95210a80066a>;rel=\"self\"]"));

        assertEquals(UUID_MOCK_CREATE, result.getId());
        assertEquals("salao", result.getNome());
        assertEquals("#salao", result.getNomeIcone());
    }

    @Test
    void testUpdate() {
        when(repository.findById(UUID_MOCK)).thenReturn(Optional.of(input.mockEntity(UUID_MOCK)));
        when(repository.save(any(Icone.class))).thenReturn(input.mockEntity(UUID_MOCK));
        when(updateMapper.apply(any(Icone.class))).thenReturn(input.mockDTOUpdated(UUID_MOCK));

        var result = service.update(input.mockDTOUpdated(UUID_MOCK));
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getNome());
        assertNotNull(result.getNomeIcone());

        assertTrue(result.toString().contains("links: [<http://localhost/api/icone/v1/7bf808f8-da36-44ea-8fbd-79653a80023e>;rel=\"self\"]"));

        assertEquals(UUID_MOCK, result.getId());
        assertEquals("salao modificado", result.getNome());
        assertEquals("#salao-modificado", result.getNomeIcone());
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
