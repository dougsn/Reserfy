package br.reserfy.integrationtests.wrappers.categoria;

import br.reserfy.domain.dto.categoria.CategoriaDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class CategoriaEmbeddedDTO implements Serializable {
    @JsonProperty("categoriaDTOList")
    private List<CategoriaDTO> users;

    public CategoriaEmbeddedDTO() {
    }

    public List<CategoriaDTO> getUsers() {
        return users;
    }

    public void setUsers(List<CategoriaDTO> users) {
        this.users = users;
    }
}
