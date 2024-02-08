package br.reserfy.integrationtests.wrappers.user;

import br.reserfy.integrationtests.dto.user.UserDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class UserEmbeddedDTO implements Serializable {
    @JsonProperty("userDTOList")
    private List<UserDTO> users;

    public UserEmbeddedDTO() {
    }

    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }
}
