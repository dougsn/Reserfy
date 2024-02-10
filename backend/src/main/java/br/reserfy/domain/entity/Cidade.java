package br.reserfy.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.io.Serializable;


@Entity
@Table(name = "cidades")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Cidade implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String nome;
    private String pais;
    private String estado;

    public Cidade() {
    }

    public Cidade(String id, String nome, String pais, String estado) {
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
