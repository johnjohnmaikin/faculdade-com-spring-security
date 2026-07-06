package br.appLogin.appLogin.service;
import br.appLogin.appLogin.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import br.appLogin.appLogin.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    public boolean autenticar(String username, String senha){
        Optional<Usuario> usuariosBuscados = usuarioRepository.findByUsername(username);

        if(usuariosBuscados.isPresent()){
            Usuario usuario= usuariosBuscados.get();

            return encoder.matches(senha, usuario.getSenha());
        }
        return false;
    }
    public boolean Cadastrar(String username, String senha, LocalDate data_nascimento, String cpf, String endereco){
        if(usuarioRepository.findByUsername(username).isPresent()){
            throw new IllegalArgumentException("Nome ja existe na base de dados!");
        }
        if(usuarioRepository.findByCpf(cpf).isPresent()){
            throw new IllegalArgumentException("cpf ja existe na base de dados!");
        }
        Usuario usuario = new Usuario(username,encoder.encode(senha),data_nascimento,cpf,endereco);
        usuarioRepository.save(usuario);
        return true;
    }
}
