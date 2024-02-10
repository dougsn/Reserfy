package br.reserfy.integrationtests.wrappers.cidade;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class WrapperCidadeDTO implements Serializable {
    @JsonProperty("_embedded")
    private CidadeEmbeddedDTO embeded;

    public WrapperCidadeDTO() {
    }

    public CidadeEmbeddedDTO getEmbeded() {
        return embeded;
    }

    public void setEmbeded(CidadeEmbeddedDTO embeded) {
        this.embeded = embeded;
    }
}
