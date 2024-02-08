package br.reserfy.integrationtests.wrappers.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class WrapperUserDTO implements Serializable {
    @JsonProperty("_embedded")
    private UserEmbeddedDTO embeded;

    public WrapperUserDTO() {
    }

    public UserEmbeddedDTO getEmbeded() {
        return embeded;
    }

    public void setEmbeded(UserEmbeddedDTO embeded) {
        this.embeded = embeded;
    }
}
