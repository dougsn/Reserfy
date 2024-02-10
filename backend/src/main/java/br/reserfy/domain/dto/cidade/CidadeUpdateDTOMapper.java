package br.reserfy.domain.dto.cidade;

import br.reserfy.domain.entity.Cidade;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CidadeUpdateDTOMapper implements Function<Cidade, CidadeUpdateDTO> {
    @Override
    public CidadeUpdateDTO apply(Cidade cidade) {
        return new CidadeUpdateDTO(
                cidade.getId(),
                cidade.getNome(),
                cidade.getPais(),
                cidade.getEstado()
        );
    }
}