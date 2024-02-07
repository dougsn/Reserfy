package br.reserfy.domain.dto.user;


import br.reserfy.domain.entity.Permission;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.List;

public class UserInfoDTO extends RepresentationModel<UserInfoDTO> implements Serializable {
    @Schema(type = "string", example = "08db0ea8-47fc-4bc0-8d00-3c02c3352785")
    private String id;
    @Schema(type = "string", example = "John")
    private String firstname;
    @Schema(type = "string", example = "Doe")
    private String lastname;
    @Schema(type = "string", example = "johndoe@gmail.com")
    private String email;
    @Schema(type = "array", example = "[ADMIN, USER]")
    private List<Permission> permissions;

    public UserInfoDTO() {
    }

    public UserInfoDTO(String id, String firstname, String lastname, String email, List<Permission> permissions) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.permissions = permissions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
