package br.reserfy.domain.dto.icone;

import br.reserfy.domain.entity.Icone;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class IconeUpdateDTOMapper implements Function<Icone, IconeUpdateDTO> {
    @Override
    public IconeUpdateDTO apply(Icone icone) {
        return new IconeUpdateDTO(
                icone.getId(),
                icone.getNome(),
                icone.getNomeIcone()
        );
    }
}