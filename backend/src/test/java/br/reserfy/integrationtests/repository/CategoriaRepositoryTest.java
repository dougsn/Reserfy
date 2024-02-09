package br.reserfy.integrationtests.repository;

import br.reserfy.domain.entity.Categoria;
import br.reserfy.domain.entity.User;
import br.reserfy.integrationtests.testcontainers.AbstractIntegrationTest;
import br.reserfy.repository.ICategoriaRepository;
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
public class CategoriaRepositoryTest { //extends AbstractIntegrationTest

    @Autowired
    ICategoriaRepository repository;
    private static Categoria categoria;

    @BeforeAll
    public static void setup() {
        categoria = new Categoria();
    }

    @Test
    public void testFindByQualificacao() {
        var categoriaByQualificacao = repository.findByQualificacao("5 Estrelas");

        assertNotNull(categoriaByQualificacao.get().getQualificacao());
        assertEquals(categoriaByQualificacao.get().getDescricao(), "Hotel 5 Estrelas");
        assertEquals(categoriaByQualificacao.get().getUrlImagem(), "https://teste5.com");
    }
}
