package br.appLogin.appLogin.controller;
import br.appLogin.appLogin.model.Usuario;
import br.appLogin.appLogin.model.TipoUsuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import br.appLogin.appLogin.service.UsuarioService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
public class LoginController {
    @Autowired
    private UsuarioService usuarioService;
    @GetMapping("/login")
    public String paginaInicial(){
        return "login";
    }

//    @PostMapping("/logar")
//    public String autenticar(@RequestParam String usuario, @RequestParam String senha, HttpSession session, RedirectAttributes redirectAttributes){
//        try {
//            if (usuarioService.autenticar(usuario, senha)) {session.setAttribute("usuarioLogado", usuario);
//                return "redirect:/index";
//            }
//        }catch (IllegalArgumentException e){
//            redirectAttributes.addFlashAttribute("erro", e.getMessage());
//        }
//        return "redirect:/login";
//    }

//    @GetMapping("/index")
//    public String deslogarNull(HttpSession session, Model model){
//        if(session.getAttribute("usuarioLogado")==null){
//            return "redirect:/login";
//        }
//        model.addAttribute("usuarioLogado", session.getAttribute("usuarioLogado"));
//        return "index";
//    }
@GetMapping("/index")
public String telaSistema(@AuthenticationPrincipal Usuario usuarioLogado, Model model) {
    model.addAttribute("usuarioLogado", usuarioLogado.getUsername());
    model.addAttribute("tipoUsuario", usuarioLogado.getTipoUsuario().name());
    return "index";
}

@GetMapping("/admin")
public String telaAdmin(){
        return "admin";
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
    public String cadastrar(@RequestParam String nome,
                            @RequestParam String senha,
                            @RequestParam LocalDate data_nascimento,
                            @RequestParam String cpf,
                            @RequestParam String endereco,
                            RedirectAttributes redirectAttributes){
        try{
            nome =nome.strip();
            usuarioService.Cadastrar(nome,senha,data_nascimento,cpf,endereco);
            redirectAttributes.addFlashAttribute("sucesso","usuário cadastrado com sucesso!");
            return "redirect:/cadastro";
        }catch (IllegalArgumentException e){
            redirectAttributes.addFlashAttribute("erro",e.getMessage());
            return "redirect:/cadastro";
        }

    }
}

