package br.reserfy.domain.dto.auth;

import br.reserfy.domain.entity.Permission;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;


public class RegisterRequest {
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
    @Schema(type = "string", example = "#Password!")
    @NotBlank(message = "O campo [password] é obrigatório,")
    private String password;
    @Schema(type = "array", example = "[{'id': 1}]")
    private List<Permission> permissions;

    public RegisterRequest() {
    }

    public RegisterRequest(String firstname, String lastname, String email, String password, List<Permission> permissions) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.permissions = permissions;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
