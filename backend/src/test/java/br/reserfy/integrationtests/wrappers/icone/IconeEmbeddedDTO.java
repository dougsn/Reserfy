package br.reserfy.integrationtests.wrappers.icone;

import br.reserfy.domain.dto.icone.IconeDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class IconeEmbeddedDTO implements Serializable {
    @JsonProperty("iconeDTOList")
    private List<IconeDTO> icones;

    public IconeEmbeddedDTO() {
    }

    public List<IconeDTO> getIcones() {
        return icones;
    }

    public void setIcones(List<IconeDTO> icones) {
        this.icones = icones;
    }
}
