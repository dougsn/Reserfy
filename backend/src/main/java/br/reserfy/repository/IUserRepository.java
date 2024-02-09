package br.reserfy.repository;

import br.reserfy.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, String> {
    @Transactional(readOnly = true)
    Optional<User> findByEmail(String email);
}
