package br.reserfy.domain.dto.user;

import br.reserfy.domain.entity.User;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserUpdateDTOMapper implements Function<User, UserUpdateDTO> {
    @Override
    public UserUpdateDTO apply(User user) {
        return new UserUpdateDTO(
                user.getId(),
                user.getFirstname(),
                user.getLastname(),
                user.getEmail(),
                user.getPassword(),
                user.getPermissions()
        );
    }
}