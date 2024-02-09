package br.reserfy.domain.dto.user;

import br.reserfy.domain.entity.User;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserInfoDTOMapper implements Function<User, UserInfoDTO> {
    @Override
    public UserInfoDTO apply(User user) {
        return new UserInfoDTO(
                user.getId(),
                user.getFirstname(),
                user.getLastname(),
                user.getEmail(),
                user.getPermissions()
        );
    }
}