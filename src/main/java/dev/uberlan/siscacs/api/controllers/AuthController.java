package dev.uberlan.siscacs.api.controllers;

import dev.uberlan.siscacs.api.request.UsuarioCreateRequest;
import dev.uberlan.siscacs.domain.Usuario;
import dev.uberlan.siscacs.domain.UsuarioService;
import dev.uberlan.siscacs.domain.command.UsuarioCreateCommand;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/login")
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String login() {
//        UsuarioLoginRequest usuario = new UsuarioLoginRequest("", "");
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println("auth = " + auth);

        return "login";
    }

    @GetMapping("/register")
    public String registerShow(Model model) {
        UsuarioCreateRequest usuario = new UsuarioCreateRequest("", "", "", "", "");
        model.addAttribute("usuario", usuario);
        return "register";
    }

    @PostMapping("/register/save")
    public String register(@ModelAttribute("usuario") UsuarioCreateRequest usuario,
                           BindingResult result,
                           Model model) {
        Optional<Usuario> usuarioByLogin = usuarioService.findUsuarioByLogin(usuario.login());
        if (usuarioByLogin.isPresent()) {
            result.rejectValue("email", null, "Já existe um usuário registrado com esse email!");
        }
        if (result.hasErrors()) {
            model.addAttribute("usuario", usuario);
            return "register";
        }

        UsuarioCreateCommand cmd = new UsuarioCreateCommand(usuario.login(), usuario.nome(), usuario.password(), usuario.cacId());
        usuarioService.createUsuario(cmd);
        return "redirect:/login";
    }
}
