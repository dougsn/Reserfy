package br.reserfy.integrationtests.dto.categoria;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;


public class CategoriaDTO implements Serializable {
    private String id;
    private String qualificacao;
    private String descricao;
    @JsonProperty("url_imagem")
    private String urlImagem;

    public CategoriaDTO() {
    }

    public CategoriaDTO(String id, String qualificacao, String descricao, String urlImagem) {
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
