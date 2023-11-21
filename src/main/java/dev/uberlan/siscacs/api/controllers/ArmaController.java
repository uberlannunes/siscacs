package dev.uberlan.siscacs.api.controllers;

import dev.uberlan.siscacs.api.request.ArmaCreateRequest;
import dev.uberlan.siscacs.api.request.ArmaUpdateRequest;
import dev.uberlan.siscacs.domain.ArmaService;
import dev.uberlan.siscacs.domain.Usuario;
import dev.uberlan.siscacs.domain.UsuarioService;
import dev.uberlan.siscacs.domain.command.ArmaCreateCommand;
import dev.uberlan.siscacs.domain.command.ArmaUpdateCommand;
import dev.uberlan.siscacs.domain.dto.ArmaDTO;
import dev.uberlan.siscacs.exception.ArmaNotFoundException;
import dev.uberlan.siscacs.exception.UsuarioNotFoundException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        Usuario usuario = usuarioService.findUsuarioByLogin(principal.getName()).orElseThrow(() -> UsuarioNotFoundException.of(principal.getName()));

        List<ArmaDTO> armas = armaService.findArmasByUsuario(usuario.getId());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("armas/armas-home");
        modelAndView.addObject("armas", armas);

        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView consultarArma(Principal principal, @PathVariable("id") UUID id) {
        ArmaDTO arma = armaService.findArmaById(id).orElseThrow(() -> ArmaNotFoundException.of(id));

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("armas/armas-view");
        modelAndView.addObject("arma", arma);

        return modelAndView;
    }

    @GetMapping("/new")
    public ModelAndView cadastrarArmaShow(Principal principal, @ModelAttribute("armaRequest") ArmaCreateRequest armaRequest) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("armas/armas-new");
        return modelAndView;
    }

    @PostMapping("/new")
    public String cadastrarArma(Principal principal, @Valid @ModelAttribute("armaRequest") ArmaCreateRequest armaRequest, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "armas/armas-new";
        }

        Usuario usuario = usuarioService.findUsuarioByLogin(principal.getName()).orElseThrow(() -> UsuarioNotFoundException.of(principal.getName()));

        ArmaCreateCommand cmd = new ArmaCreateCommand(armaRequest.calibre(), armaRequest.descricao(), armaRequest.dataCompra(), usuario.getId(), armaRequest.observacao());
        armaService.createArma(cmd);

        return "redirect:/armas";
    }

    @GetMapping("/{id}/edit")
    public String editarArmaShow(Principal principal, @PathVariable("id") UUID id, Model model) {
        ArmaDTO arma = armaService.findArmaById(id).orElseThrow(() -> ArmaNotFoundException.of(id));

        ArmaUpdateRequest armaRequest = new ArmaUpdateRequest(arma.id(), arma.calibre(), arma.descricao(), arma.dataCompra(), arma.dataVenda(), arma.observacao());
        model.addAttribute("armaRequest", armaRequest);

        return "armas/armas-edit";
    }

    @PutMapping("/{id}/edit")
    public String editarArma(Principal principal, @PathVariable("id") UUID id, @Valid @ModelAttribute("armaRequest") ArmaUpdateRequest armaRequest) {
        ArmaUpdateCommand cmd = new ArmaUpdateCommand(armaRequest.id(), armaRequest.calibre(), armaRequest.descricao(), armaRequest.dataCompra(), armaRequest.dataVenda(), armaRequest.observacao());
        armaService.updateArma(cmd);

        return "redirect:/armas";
    }
}
