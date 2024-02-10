package br.reserfy.domain.dto.cidade;

import br.reserfy.domain.entity.Cidade;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CidadeDTOMapper implements Function<Cidade, CidadeDTO> {
    @Override
    public CidadeDTO apply(Cidade cidade) {
        return new CidadeDTO(
                cidade.getId(),
                cidade.getNome(),
                cidade.getPais(),
                cidade.getEstado()
        );
    }
}
