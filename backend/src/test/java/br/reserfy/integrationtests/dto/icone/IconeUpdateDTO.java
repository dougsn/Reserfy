package br.reserfy.integrationtests.dto.icone;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class IconeUpdateDTO implements Serializable {
    private String id;
    private String nome;
    @JsonProperty("nome_icone")
    private String nomeIcone;

    public IconeUpdateDTO() {
    }

    public IconeUpdateDTO(String id, String nome, String nomeIcone) {
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
