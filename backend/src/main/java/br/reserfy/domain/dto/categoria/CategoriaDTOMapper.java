package br.reserfy.domain.dto.categoria;

import br.reserfy.domain.entity.Categoria;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CategoriaDTOMapper implements Function<Categoria, CategoriaDTO> {
    @Override
    public CategoriaDTO apply(Categoria categoria) {
        return new CategoriaDTO(
                categoria.getId(),
                categoria.getQualificacao(),
                categoria.getDescricao(),
                categoria.getUrlImagem()
        );
    }
}
