package br.reserfy.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.io.Serializable;


@Entity
@Table(name = "icones")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Icone implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String nome;
    private String nomeIcone;

    public Icone() {
    }

    public Icone(String id, String nome, String nomeIcone) {
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
