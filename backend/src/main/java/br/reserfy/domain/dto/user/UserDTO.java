package br.reserfy.domain.dto.user;

import br.reserfy.domain.entity.Permission;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.List;


public class UserDTO extends RepresentationModel<UserDTO> implements Serializable {
    @Schema(type = "string", example = "08db0ea8-47fc-4bc0-8d00-3c02c3352785")
    @NotBlank(message = "O campo [id] é obrigatório.")
    private String id;
    @Schema(type = "string", example = "John")
    @NotBlank(message = "O campo [firstname] é obrigatório.")
    private String firstname;
    @Schema(type = "string", example = "Doe")
    @NotBlank(message = "O campo [lastname] é obrigatório.")
    private String lastname;
    @Schema(type = "string", example = "johndoe@gmail.com")
    @NotBlank(message = "O campo [email] é obrigatório.")
    @Email(message = "Digite um e-mail válido.")
    private String email;
    @Schema(type = "array", example = "[ADMIN, USER]")
    private List<Permission> permissions;

    public UserDTO() {
    }

    public UserDTO(String id, String firstname, String lastname, String email, List<Permission> permissions) {
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
