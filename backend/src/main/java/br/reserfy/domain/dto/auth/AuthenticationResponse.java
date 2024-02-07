package br.reserfy.domain.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;


public class AuthenticationResponse {
    @NotBlank
    @Schema(type = "string", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkb3VnbGFzQGdtYWlsLmNvbSIsImlhdCI6MTY5NTM4MjM2NSwiZXhwIjoxNjk1NDI1NTY1fQ.6Ya3lP6pJiSPt1Rsn4vnSjWd013AbUz3x-UNHbsjv_4")
    private String token;

    public AuthenticationResponse() {
    }

    public AuthenticationResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
