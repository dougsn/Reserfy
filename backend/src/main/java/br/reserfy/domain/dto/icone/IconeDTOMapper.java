package br.reserfy.domain.dto.icone;

import br.reserfy.domain.entity.Categoria;
import br.reserfy.domain.entity.Icone;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class IconeDTOMapper implements Function<Icone, IconeDTO> {
    @Override
    public IconeDTO apply(Icone icone) {
        return new IconeDTO(
                icone.getId(),
                icone.getNome(),
                icone.getNomeIcone()
        );
    }
}
