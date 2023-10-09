package dev.uberlan.siscacs.api.controllers;

import dev.uberlan.siscacs.api.controllers.request.UsuarioCreateRequest;
import dev.uberlan.siscacs.domain.UsuarioService;
import dev.uberlan.siscacs.domain.command.UsuarioCreateCommand;
import dev.uberlan.siscacs.domain.dto.UsuarioDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
}
