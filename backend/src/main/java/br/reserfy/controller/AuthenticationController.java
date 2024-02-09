package br.reserfy.controller;

import br.reserfy.domain.dto.auth.AuthenticationRequest;
import br.reserfy.domain.dto.auth.AuthenticationResponse;
import br.reserfy.domain.dto.auth.RegisterRequest;
import br.reserfy.domain.dto.user.UserInfoDTO;
import br.reserfy.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/v1")
@Tag(description = "Registro e Login na aplicação", name = "Autenticação")
public class AuthenticationController {
    @Autowired
    private AuthenticationService service;

    @Operation(summary = "Criar conta no aplicativo")
    @ApiResponse(responseCode = "201", description = "Created", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = AuthenticationResponse.class))
    })
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterRequest request) {
        AuthenticationResponse newUser = service.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);

    }

    @Operation(summary = "Faça login no sistema")
    @ApiResponse(responseCode = "200", description = "Success", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = AuthenticationResponse.class))
    })
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authentication(@Valid @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @Operation(summary = "Obtenha informações do usuário logado")
    @ApiResponse(responseCode = "200", description = "Success", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = UserInfoDTO.class))
    })
    @GetMapping
    public ResponseEntity<UserInfoDTO> infoUser() {
        return ResponseEntity.ok(service.infoUser());
    }
}