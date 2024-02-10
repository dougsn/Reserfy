package br.reserfy.integrationtests.repository;

import br.reserfy.domain.entity.Cidade;
import br.reserfy.integrationtests.testcontainers.AbstractIntegrationTest;
import br.reserfy.repository.ICidadeRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CidadeRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    ICidadeRepository repository;
    private static Cidade cidade;

    @BeforeAll
    public static void setup() {
        cidade = new Cidade();
    }

    @Test
    public void testFindByNome() {
        var categoriaByNome = repository.findByNome("Rio de Janeiro");

        assertNotNull(categoriaByNome.get().getNome());
        assertEquals(categoriaByNome.get().getNome(), "Rio de Janeiro");
        assertEquals(categoriaByNome.get().getEstado(), "Rio de Janeiro");
        assertEquals(categoriaByNome.get().getPais(), "Brasil");
    }
}
