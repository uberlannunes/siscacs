package dev.uberlan.siscacs.api.controllers;

import dev.uberlan.siscacs.api.request.TreinamentoCreateRequest;
import dev.uberlan.siscacs.api.request.TreinamentoUpdateRequest;
import dev.uberlan.siscacs.domain.*;
import dev.uberlan.siscacs.domain.command.TreinamentoCreateCommand;
import dev.uberlan.siscacs.domain.command.TreinamentoUpdateCommand;
import dev.uberlan.siscacs.domain.dto.ArmaDTO;
import dev.uberlan.siscacs.domain.dto.TreinamentoDTO;
import dev.uberlan.siscacs.exception.ArmaNotFoundException;
import dev.uberlan.siscacs.exception.TreinamentoNotFoundException;
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
@RequestMapping("/treinamentos")
public class TreinamentoController {

    private final UsuarioService usuarioService;
    private final ArmaService armaService;
    private final MunicaoService municaoService;
    private final TreinamentoService treinamentoService;

    public TreinamentoController(UsuarioService usuarioService, ArmaService armaService, MunicaoService municaoService, TreinamentoService treinamentoService) {
        this.usuarioService = usuarioService;
        this.armaService = armaService;
        this.municaoService = municaoService;
        this.treinamentoService = treinamentoService;
    }

    @GetMapping
    public ModelAndView homeTreinamentosShow(Principal principal) {
        Usuario usuario = usuarioService.findUsuarioByLogin(principal.getName()).orElseThrow(() -> UsuarioNotFoundException.of(principal.getName()));

        List<TreinamentoDTO> treinamentos = treinamentoService.findTreinamentosByUsuarioId(usuario.getId());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("treinamentos/treinamentos-home");
        modelAndView.addObject("treinamentos", treinamentos);

        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView consultarTreinamento(Principal principal, @PathVariable("id") UUID id) {
        TreinamentoDTO treinamento = treinamentoService.findTreinamentoById(id).orElseThrow(() -> TreinamentoNotFoundException.of(id));

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("treinamentos/treinamentos-view");
        modelAndView.addObject("treinamento", treinamento);

        return modelAndView;
    }

    @GetMapping("/new")
    public ModelAndView cadastrarTreinamentoShow(Principal principal, @ModelAttribute("treinamentoRequest") TreinamentoCreateRequest treinamentoRequest) {
        Usuario usuario = usuarioService.findUsuarioByLogin(principal.getName()).orElseThrow(() -> UsuarioNotFoundException.of(principal.getName()));

        List<ArmaDTO> armas = armaService.findArmasByUsuario(usuario.getId());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("armas", armas);
        modelAndView.setViewName("treinamentos/treinamentos-new");
        return modelAndView;
    }


    @PostMapping("/new")
    public String cadastrarTreinamento(Principal principal, @ModelAttribute("treinamentoRequest") TreinamentoCreateRequest treinamentoRequest, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "treinamentos/treinamentos-new";
        }

        Usuario usuario = usuarioService.findUsuarioByLogin(principal.getName()).orElseThrow(() -> UsuarioNotFoundException.of(principal.getName()));

        ArmaDTO armaDTO = armaService.findArmaById(treinamentoRequest.armaId()).orElseThrow(() -> ArmaNotFoundException.of(treinamentoRequest.armaId()));

        TreinamentoCreateCommand cmd = new TreinamentoCreateCommand(treinamentoRequest.dataTreinamento(), new ArmaDTO(treinamentoRequest.armaId()), treinamentoRequest.quantidadeTiros(), treinamentoRequest.pontuacao(), treinamentoRequest.observacao());
        treinamentoService.createTreinamento(cmd);

        return "redirect:/treinamentos";
    }

    @GetMapping("/{id}/edit")
    public String editarTreinamentoShow(Principal principal, @PathVariable("id") UUID id, Model model) {
        System.out.println("editarTreinamentoShow > principal = " + principal);

        Usuario usuario = usuarioService.findUsuarioByLogin(principal.getName()).orElseThrow(() -> UsuarioNotFoundException.of(principal.getName()));

        TreinamentoDTO treinamentoDTO = treinamentoService.findTreinamentoById(id).orElseThrow(() -> TreinamentoNotFoundException.of(id));

        ArmaDTO armaDTO = new ArmaDTO(treinamentoDTO.arma().id(), treinamentoDTO.arma().calibre(), treinamentoDTO.arma().descricao());
        TreinamentoUpdateRequest treinamentoRequest = new TreinamentoUpdateRequest(treinamentoDTO.id(), treinamentoDTO.dataTreinamento(), treinamentoDTO.arma().id(), treinamentoDTO.quantidadeTiros(), treinamentoDTO.pontuacao(), treinamentoDTO.observacao());

        model.addAttribute("treinamentoRequest", treinamentoRequest);
        model.addAttribute("arma", armaDTO);

        return "treinamentos/treinamentos-edit";
    }

    @PutMapping("/{id}/edit")
    public String editarTreinamento(Principal principal, @PathVariable("id") UUID id, @Valid @ModelAttribute("treinamentoRequest") TreinamentoUpdateRequest treinamentoRequest) {
        System.out.println("editarTreinamento > principal = " + principal);
        System.out.println("editarTreinamento > treinamentoRequest = " + treinamentoRequest);

        TreinamentoUpdateCommand cmd = new TreinamentoUpdateCommand(id, treinamentoRequest.dataTreinamento(), new ArmaDTO(treinamentoRequest.armaId()), treinamentoRequest.quantidadeTiros(), treinamentoRequest.pontuacao(), treinamentoRequest.observacao());
        treinamentoService.updateTreinamento(cmd);

        return "redirect:/treinamentos";
    }
}
