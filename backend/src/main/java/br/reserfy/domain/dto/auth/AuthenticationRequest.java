package br.reserfy.domain.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class AuthenticationRequest {
    @Schema(type = "string", example = "username")
    @NotBlank(message = "O campo [email] é obrigatório.")
    @Email(message = "Digite um e-mail válido.")
    private String email;

    @Schema(type = "string", example = "#Password!")
    @NotBlank(message = "O campo [password] é obrigatório.")
    private String password;

    public AuthenticationRequest() {
    }

    public AuthenticationRequest(String email, String password) {
        this.email = email;
        this.password = password;
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
}
