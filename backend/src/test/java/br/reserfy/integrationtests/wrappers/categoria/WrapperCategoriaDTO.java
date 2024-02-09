package br.reserfy.integrationtests.wrappers.categoria;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class WrapperCategoriaDTO implements Serializable {
    @JsonProperty("_embedded")
    private CategoriaEmbeddedDTO embeded;

    public WrapperCategoriaDTO() {
    }

    public CategoriaEmbeddedDTO getEmbeded() {
        return embeded;
    }

    public void setEmbeded(CategoriaEmbeddedDTO embeded) {
        this.embeded = embeded;
    }
}
