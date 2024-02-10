package br.reserfy.integrationtests.wrappers.icone;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class WrapperIconeDTO implements Serializable {
    @JsonProperty("_embedded")
    private IconeEmbeddedDTO embeded;

    public WrapperIconeDTO() {
    }

    public IconeEmbeddedDTO getEmbeded() {
        return embeded;
    }

    public void setEmbeded(IconeEmbeddedDTO embeded) {
        this.embeded = embeded;
    }
}
