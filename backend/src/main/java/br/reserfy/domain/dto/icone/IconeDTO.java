package br.reserfy.domain.dto.icone;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;


public class IconeDTO extends RepresentationModel<IconeDTO> implements Serializable {
    @Schema(type = "string", example = "08db0ea8-47fc-4bc0-8d00-3c02c3352785")
    private String id;
    @Schema(type = "string", example = "wi-fi")
    @NotEmpty(message = "O campo [nome] é obrigatório.")
    @Length(max = 50, message = "O campo [nome] não pode ser maior que 50 caracteres")
    private String nome;
    @Schema(type = "string", example = "Hotel em frente à praia")
    @NotEmpty(message = "O campo [#wi-fi] é obrigatório.")
    @Length(max = 50, message = "O campo [nome_icone] não pode ser maior que 50 caracteres")
    @JsonProperty("nome_icone")
    private String nomeIcone;

    public IconeDTO() {
    }

    public IconeDTO(String id, String nome, String nomeIcone) {
        this.id = id;
        this.nome = nome;
        this.nomeIcone = nomeIcone;
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

    public String getNomeIcone() {
        return nomeIcone;
    }

    public void setNomeIcone(String nomeIcone) {
        this.nomeIcone = nomeIcone;
    }
}
