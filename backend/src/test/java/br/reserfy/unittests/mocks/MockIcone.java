package br.reserfy.unittests.mocks;

import br.reserfy.domain.dto.icone.IconeDTO;
import br.reserfy.domain.dto.icone.IconeUpdateDTO;
import br.reserfy.domain.entity.Icone;

public class MockIcone {
    public Icone mockEntity(String id) {
        Icone icone = new Icone();
        icone.setId(id);
        icone.setNome("salao");
        icone.setNomeIcone("#salao");
        return icone;
    }

    public IconeDTO mockDTO(String id) {
        IconeDTO dto = new IconeDTO();
        dto.setId(id);
        dto.setNome("salao");
        dto.setNomeIcone("#salao");
        return dto;
    }

    public IconeUpdateDTO mockDTOUpdated(String id) {
        IconeUpdateDTO dto = new IconeUpdateDTO();
        dto.setId(id);
        dto.setNome("salao modificado");
        dto.setNomeIcone("#salao-modificado");
        return dto;
    }
}
