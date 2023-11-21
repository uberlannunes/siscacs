package dev.uberlan.siscacs.api.controllers;

import dev.uberlan.siscacs.api.request.UsuarioChangePasswordRequest;
import dev.uberlan.siscacs.api.request.UsuarioCreateRequest;
import dev.uberlan.siscacs.api.request.UsuarioUpdateRequest;
import dev.uberlan.siscacs.domain.Usuario;
import dev.uberlan.siscacs.domain.UsuarioService;
import dev.uberlan.siscacs.domain.command.UsuarioCreateCommand;
import dev.uberlan.siscacs.domain.command.UsuarioUpdateDadosPessoaisCommand;
import dev.uberlan.siscacs.domain.command.UsuarioUpdatePasswordCommand;
import dev.uberlan.siscacs.domain.dto.UsuarioDTO;
import dev.uberlan.siscacs.exception.UsuarioNotFoundException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ModelAndView consultarUsuarios() {
        List<UsuarioDTO> usuarios = usuarioService.findAllUsuarios();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("usuarios", usuarios);
        modelAndView.setViewName("usuarios/usuarios-home");
        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView consultarUsuarioById(@PathVariable("codigoProtocolo") String id) {
        Optional<UsuarioDTO> usuario = usuarioService.findUsuarioById(UUID.fromString(id));

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("usuario", usuario);
        modelAndView.setViewName("usuarios/usuarios-view");
        return modelAndView;
    }

    @GetMapping("/new")
    public ModelAndView criarUsuario() {
        UsuarioCreateRequest request = new UsuarioCreateRequest("usuario01", "Usuário 01", "123456", "cac123", "");

        UsuarioDTO usuario = usuarioService.createUsuario(new UsuarioCreateCommand(request.login(), request.nome(), request.password(), request.cacId()));

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("usuario", usuario);
        modelAndView.setViewName("usuarios/usuarios-view");
        return modelAndView;
    }

    @GetMapping("/home")
    public String homeShow() {
        return "usuarios/usuarios-home";
    }

    @GetMapping("/view")
    public String dadosPessoaisShow(Principal principal, Model model) {
        Usuario usuario = usuarioService.findUsuarioByLogin(principal.getName()).orElseThrow(() -> UsuarioNotFoundException.of(principal.getName()));
        UsuarioDTO usuarioDTO = new UsuarioDTO(usuario.getId(), usuario.getLogin(), usuario.getNome(), usuario.getCacId());
        model.addAttribute("usuario", usuarioDTO);

        return "usuarios/usuarios-view";
    }

    @GetMapping("/edit")
    public String editDadosPessoaisShow(Principal principal, Model model) {
        Usuario usuario = usuarioService.findUsuarioByLogin(principal.getName()).orElseThrow(() -> UsuarioNotFoundException.of(principal.getName()));
        UsuarioUpdateRequest usuarioUpdateRequest = new UsuarioUpdateRequest(usuario.getLogin(), usuario.getNome(), usuario.getCacId());
        model.addAttribute("usuarioRequest", usuarioUpdateRequest);

        return "usuarios/usuarios-edit";
    }

    @PutMapping("/edit")
    public String editDadosPessoais(Principal principal, @Valid @ModelAttribute("usuarioRequest") UsuarioUpdateRequest usuarioRequest) {
        Usuario usuario = usuarioService.findUsuarioByLogin(principal.getName()).orElseThrow(() -> UsuarioNotFoundException.of(principal.getName()));

        UsuarioUpdateDadosPessoaisCommand cmd = new UsuarioUpdateDadosPessoaisCommand(usuario.getId(), usuarioRequest.nome(), usuarioRequest.cacId());
        usuarioService.updateDadosPessoais(cmd);

        return "usuarios/usuarios-home";
    }

    @GetMapping("/password")
    public String passwordShow(@ModelAttribute("usuarioPasswordRequest") UsuarioChangePasswordRequest usuarioPasswordRequest) {
        return "usuarios/usuarios-password";
    }

    @PutMapping("/password")
    public String passwordShow(Principal principal, @Valid @ModelAttribute("usuarioPasswordRequest") UsuarioChangePasswordRequest usuarioPasswordRequest, BindingResult bindingResult) {
        if (!usuarioPasswordRequest.passwordNew().equals(usuarioPasswordRequest.passwordNewConfirm())) {
            bindingResult.rejectValue("passwordNewConfirm", "usuarioPasswordRequest.passwordNewConfirm", "O novo password e confirmação não são iguais!");
        }

        if (bindingResult.hasErrors()) {
            System.out.println("bindingResult.toString() = " + bindingResult.toString());
            return "usuarios/usuarios-password";
        }

        Usuario usuario = usuarioService.findUsuarioByLogin(principal.getName()).orElseThrow(() -> UsuarioNotFoundException.of(principal.getName()));

        UsuarioUpdatePasswordCommand cmd = new UsuarioUpdatePasswordCommand(usuario.getId(), usuarioPasswordRequest.passwordNew());
        usuarioService.updatePassword(cmd);

        return "redirect:/usuarios/home";
    }
}
