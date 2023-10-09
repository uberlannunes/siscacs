package dev.uberlan.siscacs.api.controllers;

import dev.uberlan.siscacs.api.controllers.request.UsuarioCreateRequest;
import dev.uberlan.siscacs.api.controllers.request.UsuarioLoginRequest;
import dev.uberlan.siscacs.domain.Usuario;
import dev.uberlan.siscacs.domain.UsuarioService;
import dev.uberlan.siscacs.domain.command.UsuarioCreateCommand;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        UsuarioLoginRequest usuario = new UsuarioLoginRequest("", "");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("auth = " + auth);

        return "login";
    }

//    @GetMapping(value = {"/", "/login"})
//    public String viewLogin(@RequestParam(value = "error", defaultValue = "false") boolean loginError,
//                            @RequestParam(value = "userName", required = false) String userName,
//                            @RequestParam(value = "password", required = false) String password,
//                            RedirectAttributes redirectAttributes) {
//
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//
//        /*
//         * Se o usuário já estiver autenticado e tentar acessar a página de login, é
//         * redirecionado para a página home (Home Principal).
//         */
//        if (!(auth instanceof AnonymousAuthenticationToken)) {
//            return "redirect:/home";
//        } else {
//
//            if (loginError) {
//                redirectAttributes.addFlashAttribute("userName", userName);
//                redirectAttributes.addFlashAttribute("password", password);
//                redirectAttributes.addFlashAttribute("errorMessage", "Email ou senha inválidos.");
//                return "redirect:/login";
//            }
//
//            return "login";
//        }
//
//    }

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
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        if (result.hasErrors()) {
            model.addAttribute("usuario", usuario);
            return "register";
        }

        UsuarioCreateCommand cmd = new UsuarioCreateCommand(usuario.login(), usuario.nome(), usuario.password(), usuario.cacId());
        usuarioService.createUsuario(cmd);
        return "redirect:/login?success";
    }
}
