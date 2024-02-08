package br.reserfy.unittests.mocks;

import br.reserfy.domain.dto.user.UserDTO;
import br.reserfy.domain.entity.Permission;
import br.reserfy.domain.entity.User;

import java.util.Collections;

public class MockUser {

    public User mockEntityAdmin(String id) {
        User user = new User();
        user.setId(id);
        user.setFirstname("Teste");
        user.setLastname("Mock");
        user.setEmail("teste@gmail.com");
        user.setPassword("admin123");
        user.setPermissions(Collections.singletonList(adminPermission()));
        return user;
    }

    public UserDTO mockDTOAdmin(String id) {
        UserDTO dto = new UserDTO();
        dto.setId(id);
        dto.setFirstname("Teste");
        dto.setLastname("Mock");
        dto.setEmail("teste@gmail.com");
        dto.setPermissions(Collections.singletonList(adminPermission()));
        return dto;
    }

    public User mockEntity(String id) {
        User user = new User();
        user.setId(id);
        user.setFirstname("Teste");
        user.setLastname("Mock");
        user.setEmail("teste@gmail.com");
        return user;
    }

    public UserDTO mockDTO(String id) {
        UserDTO dto = new UserDTO();
        dto.setId(id);
        dto.setFirstname("Teste");
        dto.setLastname("Mock");
        dto.setEmail("teste@gmail.com");
        dto.setPermissions(Collections.singletonList(adminPermission()));
        return dto;
    }

    public UserDTO mockDTOUpdated(String id) {
        UserDTO dto = new UserDTO();
        dto.setId(id);
        dto.setFirstname("Teste Modificado");
        dto.setLastname("Mock Modificado");
        dto.setEmail("testemodificado@gmail.com");
        dto.setPermissions(Collections.singletonList(managerPermission()));
        return dto;
    }

    public Permission adminPermission() {
        Permission permission = new Permission();
        permission.setId(1L);
        permission.setDescription("ADMIN");
        return permission;
    }

    public Permission managerPermission() {
        Permission permission = new Permission();
        permission.setId(2L);
        permission.setDescription("MANAGER");
        return permission;
    }

}
