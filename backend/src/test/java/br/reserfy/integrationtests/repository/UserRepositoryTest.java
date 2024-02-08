package br.reserfy.integrationtests.repository;

import br.reserfy.domain.entity.User;
import br.reserfy.integrationtests.testcontainers.AbstractIntegrationTest;
import br.reserfy.repository.IUserRepository;
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
public class UserRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    IUserRepository repository;
    private static User user;

    @BeforeAll
    public static void setup() {
        user = new User();
    }

    @Test
    public void testFindByEmail() {
        var userByUsername = repository.findByEmail("admin@gmail.com");

        assertNotNull(userByUsername.get().getUsername());
        assertEquals(userByUsername.get().getFirstname(), "Administrator");
        assertEquals(userByUsername.get().getLastname(), "System");
        assertEquals(userByUsername.get().getEmail(), "admin@gmail.com");
    }
}
