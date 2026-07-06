package br.appLogin.appLogin.controller;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import br.appLogin.appLogin.service.UsuarioService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;
import java.time.LocalDate;

@Controller
public class LoginController {
    @Autowired
    private UsuarioService usuarioService;
    @GetMapping("/")
    public String raiz(){
        return "redirect:/login";
    }
    @GetMapping("/login")
    public String paginaInicial(){
        return "login";
    }

    @PostMapping("/logar")
    public String autenticar(@RequestParam String usuario,
                             @RequestParam String senha,
                             HttpSession session, RedirectAttributes redirectAttributes){
        if(usuarioService.autenticar(usuario, senha)){
           session.setAttribute("usuarioLogado", usuario);
           return "redirect:/index";
        }
        redirectAttributes.addFlashAttribute("erro", "Usuário ou senha invalidos!");
        return "redirect:/login";
    }

    @GetMapping("/index")
    public String deslogarNull(HttpSession session, Model model){
        if(session.getAttribute("usuarioLogado")==null){
            return "redirect:/login";
        }
        model.addAttribute("usuarioLogado", session.getAttribute("usuarioLogado"));
        return "index";
    }
    @GetMapping("/logout")
    public String deslogar(HttpSession session){
        session.invalidate();
        return "redirect:/login";
    }
    @GetMapping("/cadastro")
    public String cadastrarUsuario(){
        return "cadastro";
    }
    @PostMapping("/cadastrar")
    public String cadastrar(@RequestParam String nome, String senha, LocalDate data_nascimento, String cpf, String endereco, RedirectAttributes redirectAttributes){
        try{
            usuarioService.Cadastrar(nome,senha,data_nascimento,cpf,endereco);
            redirectAttributes.addFlashAttribute("sucesso","usuário cadastrado com sucesso!");
            return "cadastro";
        }catch (IllegalArgumentException e){
            redirectAttributes.addFlashAttribute("erro",e.getMessage());
            return "redirect:/cadastro";
        }

    }
}

