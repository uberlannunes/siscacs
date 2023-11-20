package dev.uberlan.siscacs.api.controllers;

import dev.uberlan.siscacs.domain.TreinamentoService;
import dev.uberlan.siscacs.domain.Usuario;
import dev.uberlan.siscacs.domain.UsuarioService;
import dev.uberlan.siscacs.domain.dto.TreinamentoDTO;
import dev.uberlan.siscacs.exception.UsuarioNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {

    private final UsuarioService usuarioService;
    private final TreinamentoService treinamentoService;

    public HomeController(UsuarioService usuarioService, TreinamentoService treinamentoService) {
        this.usuarioService = usuarioService;
        this.treinamentoService = treinamentoService;
    }

    @GetMapping("/home")
    public ModelAndView home(Principal principal) {
        Usuario usuario = usuarioService.findUsuarioByLogin(principal.getName()).orElseThrow(() -> UsuarioNotFoundException.of(principal.getName()));

        List<TreinamentoDTO> treinamentos = treinamentoService.findTreinamentosByUsuarioId(usuario.getId());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        modelAndView.addObject("treinamentos", treinamentos);

        return modelAndView;
    }
}
