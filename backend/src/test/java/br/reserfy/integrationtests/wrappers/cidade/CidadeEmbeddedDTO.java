package br.reserfy.integrationtests.wrappers.cidade;

import br.reserfy.domain.dto.cidade.CidadeDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class CidadeEmbeddedDTO implements Serializable {
    @JsonProperty("cidadeDTOList")
    private List<CidadeDTO> cidades;

    public CidadeEmbeddedDTO() {
    }

    public List<CidadeDTO> getCidades() {
        return cidades;
    }

    public void setCidades(List<CidadeDTO> cidades) {
        this.cidades = cidades;
    }
}
