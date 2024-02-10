package br.reserfy.unittests.mocks;

import br.reserfy.domain.dto.cidade.CidadeDTO;
import br.reserfy.domain.dto.cidade.CidadeUpdateDTO;
import br.reserfy.domain.entity.Cidade;

public class MockCidade {
    public Cidade mockEntity(String id) {
        Cidade cidade = new Cidade();
        cidade.setId(id);
        cidade.setNome("Rio de Janeiro");
        cidade.setPais("Brasil");
        cidade.setEstado("Rio de Janeiro");
        return cidade;
    }

    public CidadeDTO mockDTO(String id) {
        CidadeDTO dto = new CidadeDTO();
        dto.setId(id);
        dto.setNome("Rio de Janeiro");
        dto.setPais("Brasil");
        dto.setEstado("Rio de Janeiro");
        return dto;
    }

    public CidadeUpdateDTO mockDTOUpdated(String id) {
        CidadeUpdateDTO dto = new CidadeUpdateDTO();
        dto.setId(id);
        dto.setNome("Rio de Janeiro");
        dto.setPais("Brasil");
        dto.setEstado("Rio de Janeiro");
        return dto;
    }
}
