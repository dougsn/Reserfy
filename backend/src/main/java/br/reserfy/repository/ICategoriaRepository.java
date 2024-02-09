package br.reserfy.repository;

import br.reserfy.domain.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ICategoriaRepository extends JpaRepository<Categoria, String> {
    @Transactional(readOnly = true)
    Optional<Categoria> findByQualificacao(String qualificacao);
}
