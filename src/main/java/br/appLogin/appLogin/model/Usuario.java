package br.appLogin.appLogin.model;

import jakarta.persistence.*;
import java.time.LocalDate;
// ADICIONE ESTAS TRÊS IMPORTAÇÕES DO SPRING SECURITY
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name="usuarios")
public class Usuario implements UserDetails { // 1. Adicione o 'implements UserDetails' aqui

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public Usuario(){}

    // --- MÉTODOS OBRIGATÓRIOS DO SPRING SECURITY (ADICIONE ESTE BLOCO) ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Define o perfil padrão do usuário logado no sistema
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        // O Spring Security precisa saber qual atributo guarda a sua senha criptografada
        return this.senha;
    }

    @Override
    public String getUsername() {
        // O Spring Security precisa saber qual atributo guarda o login do usuário
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Conta não expirada
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Conta não bloqueada
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Senha/Credenciais não expiradas
    }

    @Override
    public boolean isEnabled() {
        // Vincula o status ativo do Spring Security ao seu atributo 'ativo' do banco
        return this.ativo;
    }

    // --- SEUS GETTERS E SETTERS ATUAIS (MANTIDOS IGUAIS) ---
    // Você pode manter ou remover o getUsername original, pois o método do UserDetails acima faz o mesmo papel

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
    public LocalDate getData_nascimento() { return data_nascimento; }
    public void setData_nascimento(LocalDate data_nascimento) { this.data_nascimento = data_nascimento; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
}
