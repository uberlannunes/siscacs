package dev.uberlan.siscacs.api.controllers;

import dev.uberlan.siscacs.api.request.ArmaCreateRequest;
import dev.uberlan.siscacs.domain.ArmaService;
import dev.uberlan.siscacs.domain.Usuario;
import dev.uberlan.siscacs.domain.UsuarioService;
import dev.uberlan.siscacs.domain.command.ArmaCreateCommand;
import dev.uberlan.siscacs.domain.dto.ArmaDTO;
import dev.uberlan.siscacs.exception.ArmaNotFoundException;
import dev.uberlan.siscacs.exception.UsuarioNotFoundException;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/armas")
public class ArmaController {

    private final UsuarioService usuarioService;
    private final ArmaService armaService;

    public ArmaController(UsuarioService usuarioService, ArmaService armaService) {
        this.usuarioService = usuarioService;
        this.armaService = armaService;
    }

    @GetMapping
    public ModelAndView consultarArmas(Principal principal) {
        System.out.println("principal = " + principal);

        Usuario usuario = usuarioService.findUsuarioByLogin(principal.getName()).orElseThrow(() -> UsuarioNotFoundException.of(principal.getName()));

        List<ArmaDTO> armas = armaService.findArmasByUsuario(usuario.getId());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("armas/armas-home");
        modelAndView.addObject("armas", armas);

        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView consultarArma(Principal principal, @PathVariable("id") UUID id) {
        System.out.println("principal = " + principal);

        ArmaDTO arma = armaService.findArmaById(id).orElseThrow(() -> ArmaNotFoundException.of(id));

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("armas/armas-view");
        modelAndView.addObject("arma", arma);

        return modelAndView;
    }

    @GetMapping("/new")
    public ModelAndView cadastrarArmaShow(Principal principal, @ModelAttribute("armaRequest") ArmaCreateRequest armaRequest) {
        System.out.println("principal = " + principal);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("armas/armas-new");
        return modelAndView;
    }

    @PostMapping("/new")
    public String cadastrarArma(Principal principal, @Valid @ModelAttribute("armaRequest") ArmaCreateRequest armaRequest, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        System.out.println("principal = " + principal);
        System.out.println("principal.getName = " + principal.getName());
        System.out.println("armaRequest = " + armaRequest);

        if (bindingResult.hasErrors()) {
            System.out.println("bindingResult.toString() = " + bindingResult.toString());
            return "armas/armas-new";
        }

        Usuario usuario = usuarioService.findUsuarioByLogin(principal.getName()).orElseThrow(() -> UsuarioNotFoundException.of(principal.getName()));

        ArmaCreateCommand cmd = new ArmaCreateCommand(armaRequest.calibre(), armaRequest.descricao(), armaRequest.dataCompra(), usuario.getId(), armaRequest.observacao());
        armaService.createArma(cmd);

        return "armas/armas-home";
    }
}
