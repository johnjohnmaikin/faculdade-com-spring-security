package br.appLogin.appLogin.model;

import jakarta.persistence.*;

import java.sql.Date;
import java.time.LocalDate;


@Entity
    @Table(name="usuarios")
    public class Usuario {

        @Id
        @GeneratedValue( strategy = GenerationType.IDENTITY)

        @Column(nullable = false, unique = true)
        private Long id;

        @Column(nullable = false, unique = true)
        private String username;

        @Column(nullable = false, length = 100)
        private String senha;

        private LocalDate data_nascimento;

        @Column(nullable = false, unique = true)
        private String cpf;

        private String endereco;

        private boolean ativo = false;


    public Usuario(String username, String senha, LocalDate data_nascimento, String cpf, String endereco) {
        this.username = username;
        this.senha = senha;
        this.data_nascimento = data_nascimento;
        this.cpf = cpf;
        this.endereco = endereco;
    }
    public Usuario(){};

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getSenha() {
        return senha;
    }

    public LocalDate getData_nascimento() {
        return data_nascimento;
    }

    public void setData_nascimento(LocalDate data_nascimento) {
        this.data_nascimento = data_nascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setSenha(String senha) {
            this.senha = senha;
        }
    }

