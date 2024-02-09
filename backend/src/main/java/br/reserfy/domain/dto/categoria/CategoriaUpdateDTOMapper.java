package br.reserfy.domain.dto.categoria;

import br.reserfy.domain.entity.Categoria;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CategoriaUpdateDTOMapper implements Function<Categoria, CategoriaUpdateDTO> {
    @Override
    public CategoriaUpdateDTO apply(Categoria categoria) {
        return new CategoriaUpdateDTO(
                categoria.getId(),
                categoria.getQualificacao(),
                categoria.getDescricao(),
                categoria.getUrlImagem()
        );
    }
}