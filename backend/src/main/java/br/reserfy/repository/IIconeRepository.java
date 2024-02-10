package br.reserfy.repository;

import br.reserfy.domain.entity.Cidade;
import br.reserfy.domain.entity.Icone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface IIconeRepository extends JpaRepository<Icone, String> {
    @Transactional(readOnly = true)
    Optional<Icone> findByNome(String nome);
}
