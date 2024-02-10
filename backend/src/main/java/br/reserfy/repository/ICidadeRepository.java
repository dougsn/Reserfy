package br.reserfy.repository;

import br.reserfy.domain.entity.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ICidadeRepository extends JpaRepository<Cidade, String> {
    @Transactional(readOnly = true)
    Optional<Cidade> findByNome(String nome);
}
