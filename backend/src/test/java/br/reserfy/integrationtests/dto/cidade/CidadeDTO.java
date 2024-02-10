package br.reserfy.integrationtests.dto.cidade;

import java.io.Serializable;


public class CidadeDTO implements Serializable {
    private String id;
    private String nome;
    private String pais;

    private String estado;

    public CidadeDTO() {
    }

    public CidadeDTO(String id, String nome, String pais, String estado) {
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
