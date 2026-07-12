package br.appLogin.appLogin.service;
import br.appLogin.appLogin.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import br.appLogin.appLogin.repository.UsuarioRepository;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username).get();

        return usuario;
    }

    public boolean Cadastrar(String username, String senha, LocalDate data_nascimento, String cpf, String endereco) {

        cpf = cpf.replaceAll("\\D", "");
        if (usuarioRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Nome ja existe na base de dados!");
        }
        validaCpf(cpf);
        if (usuarioRepository.findByCpf(cpf).isPresent()) {
            throw new IllegalArgumentException("cpf ja existe na base de dados!");
        }

        Usuario usuario = new Usuario(username, encoder.encode(senha), data_nascimento, cpf, endereco);
        usuarioRepository.save(usuario);

        return true;
    }

    public boolean validaCpf(String cpf) {
        // CPF precisa ter exatamente 11 dígitos
        if (cpf.length() != 11) throw new IllegalArgumentException("Cpf não corresponde ao tamanho correto!");

        // Ignora CPFs com todos os dígitos iguais (ex: 11111111111) ou (22222222222)
        if (cpf.matches("(\\d)\\1{10}")) throw new IllegalArgumentException("Cpf não corresponde a um cpf válido!");

        return true;
    }

    public List<Usuario> buscarTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarUsuarioId(Long id) {
        Optional<Usuario> usuarioBuscado = usuarioRepository.findById(id);
        if (usuarioBuscado.isPresent()) {
            Usuario usuarios = usuarioBuscado.get();
            return usuarios;
        }
        throw new IllegalArgumentException("nenhum Usuário encontrado!");
    }

    public void ativarDesativar(String username, String situacao) {
        Optional<Usuario> usuarioBuscado = usuarioRepository.findByUsername(username);
        Usuario usuario = usuarioBuscado.get();
        if(situacao.equals("ativo")){
            usuario.setAtivo(true);
        }else{usuario.setAtivo(false);}
        usuarioRepository.save(usuario);
    }
}
/*public boolean autenticar(String username, String senha){
        Optional<Usuario> usuariosBuscados = usuarioRepository.findByUsername(username);

        if(usuariosBuscados.isEmpty()){throw new IllegalArgumentException("Usuário ou senha invalidos!");}

        Usuario usuario = usuariosBuscados.get();

        if (!encoder.matches(senha, usuario.getSenha())) {throw new IllegalArgumentException("Usuário ou senha invalidos!");}

        if (!usuario.isAtivo()) {throw new IllegalArgumentException("Usuário aguardando aprovação de cadastro.");}

        return true;

    }*/

