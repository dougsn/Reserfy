package br.reserfy.domain.dto.cidade;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

public class CidadeUpdateDTO extends RepresentationModel<CidadeUpdateDTO> implements Serializable {
    @Schema(type = "string", example = "08db0ea8-47fc-4bc0-8d00-3c02c3352785")
    @NotEmpty(message = "O campo [id] é obrigatório.")
    private String id;
    @Schema(type = "string", example = "Rio de Janeiro")
    @NotEmpty(message = "O campo [nome] é obrigatório.")
    @Length(max = 70, message = "O campo [nome] não pode ser maior que 70 caracteres")
    private String nome;
    @Schema(type = "string", example = "Brasil")
    @NotEmpty(message = "O campo [pais] é obrigatório.")
    @Length(max = 50, message = "O campo [pais] não pode ser maior que 50 caracteres")
    private String pais;

    @Schema(type = "string", example = "Rio de Janeiro")
    @NotEmpty(message = "O campo [estado] é obrigatório.")
    @Length(max = 50, message = "O campo [estado] não pode ser maior que 50 caracteres")
    private String estado;

    public CidadeUpdateDTO() {
    }

    public CidadeUpdateDTO(String id, String nome, String pais, String estado) {
        this.id = id;
        this.nome = nome;
        this.pais = pais;
        this.estado = estado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
