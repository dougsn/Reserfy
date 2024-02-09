package br.reserfy.domain.dto.categoria;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

public class CategoriaUpdateDTO extends RepresentationModel<CategoriaUpdateDTO> implements Serializable {
    @Schema(type = "string", example = "08db0ea8-47fc-4bc0-8d00-3c02c3352785")
    @NotEmpty(message = "O campo [id] é obrigatório.")
    private String id;
    @Schema(type = "string", example = "Bom")
    @NotEmpty(message = "O campo [qualificacao] é obrigatório.")
    @Length(max = 100, message = "O campo [qualificacao] não pode ser maior que 100 caracteres")
    private String qualificacao;
    @Schema(type = "string", example = "Hotel em frente à praia")
    @NotEmpty(message = "O campo [descricao] é obrigatório.")
    private String descricao;
    @Schema(type = "string", example = "https://url.com")
    @NotEmpty(message = "O campo [url_imagem] é obrigatório.")
    @Length(max = 100, message = "O campo [url_imagem] não pode ser maior que 100 caracteres")
    @JsonProperty("url_imagem")
    private String urlImagem;

    public CategoriaUpdateDTO() {
    }

    public CategoriaUpdateDTO(String id, String qualificacao, String descricao, String urlImagem) {
        this.id = id;
        this.qualificacao = qualificacao;
        this.descricao = descricao;
        this.urlImagem = urlImagem;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQualificacao() {
        return qualificacao;
    }

    public void setQualificacao(String qualificacao) {
        this.qualificacao = qualificacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }
}
