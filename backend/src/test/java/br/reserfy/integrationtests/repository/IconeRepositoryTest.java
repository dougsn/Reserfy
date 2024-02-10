package br.reserfy.integrationtests.repository;

import br.reserfy.domain.entity.Icone;
import br.reserfy.integrationtests.testcontainers.AbstractIntegrationTest;
import br.reserfy.repository.IIconeRepository;
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
public class IconeRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    IIconeRepository repository;
    private static Icone icone;

    @BeforeAll
    public static void setup() {
        icone = new Icone();
    }

    @Test
    public void testFindByNome() {
        var iconeByNome = repository.findByNome("wifi");

        assertNotNull(iconeByNome.get().getNome());
        assertEquals(iconeByNome.get().getNome(), "wifi");
        assertEquals(iconeByNome.get().getNomeIcone(), "#wifi");
    }
}
