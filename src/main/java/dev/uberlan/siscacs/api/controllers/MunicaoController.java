package dev.uberlan.siscacs.api.controllers;

import dev.uberlan.siscacs.api.request.MunicaoCreateRequest;
import dev.uberlan.siscacs.api.request.MunicaoUpdateRequest;
import dev.uberlan.siscacs.domain.ArmaService;
import dev.uberlan.siscacs.domain.MunicaoService;
import dev.uberlan.siscacs.domain.Usuario;
import dev.uberlan.siscacs.domain.UsuarioService;
import dev.uberlan.siscacs.domain.command.MunicaoCreateCommand;
import dev.uberlan.siscacs.domain.command.MunicaoUpdateCommand;
import dev.uberlan.siscacs.domain.dto.ArmaDTO;
import dev.uberlan.siscacs.domain.dto.MunicaoDTO;
import dev.uberlan.siscacs.exception.ArmaNotFoundException;
import dev.uberlan.siscacs.exception.MunicaoNotFoundException;
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
@RequestMapping("/municoes")
public class MunicaoController {

    private final UsuarioService usuarioService;
    private final ArmaService armaService;
    private final MunicaoService municaoService;

    public MunicaoController(UsuarioService usuarioService, ArmaService armaService, MunicaoService municaoService) {
        this.usuarioService = usuarioService;
        this.armaService = armaService;
        this.municaoService = municaoService;
    }

    @GetMapping
    public ModelAndView consultarMunicoes(Principal principal) {
        System.out.println("consultarMunicoes > principal = " + principal);

        Usuario usuario = usuarioService.findUsuarioByLogin(principal.getName()).orElseThrow(() -> UsuarioNotFoundException.of(principal.getName()));

        List<MunicaoDTO> municoes = municaoService.findMunicoesByUsuarioId(usuario.getId());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("municoes/municoes-home");
        modelAndView.addObject("municoes", municoes);

        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView consultarMunicao(Principal principal, @PathVariable("id") UUID id) {
        System.out.println("consultarMunicao > principal = " + principal);

        MunicaoDTO municao = municaoService.findMunicaoById(id).orElseThrow(() -> MunicaoNotFoundException.of(id));

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("municoes/municoes-view");
        modelAndView.addObject("municao", municao);

        return modelAndView;
    }

    @GetMapping("/new")
    public ModelAndView cadastrarMunicaoShow(Principal principal, @ModelAttribute("municaoRequest") MunicaoCreateRequest municaoRequest) {
        System.out.println("cadastrarMunicaoShow > principal = " + principal);

        Usuario usuario = usuarioService.findUsuarioByLogin(principal.getName()).orElseThrow(() -> UsuarioNotFoundException.of(principal.getName()));

        List<ArmaDTO> armas = armaService.findArmasByUsuario(usuario.getId());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("armas", armas);
        modelAndView.setViewName("municoes/municoes-new");
        return modelAndView;
    }

    @PostMapping("/new")
    public String cadastrarMunicao(Principal principal, @ModelAttribute("municaoRequest") MunicaoCreateRequest municaoRequest, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        System.out.println("cadastrarMunicao > principal = " + principal);

        if (bindingResult.hasErrors()) {
            System.out.println("bindingResult.toString() = " + bindingResult.toString());
            return "municoes/municoes-new";
        }

        Usuario usuario = usuarioService.findUsuarioByLogin(principal.getName()).orElseThrow(() -> UsuarioNotFoundException.of(principal.getName()));

        ArmaDTO armaDTO = armaService.findArmaById(municaoRequest.armaId()).orElseThrow(() -> ArmaNotFoundException.of(municaoRequest.armaId()));

        MunicaoCreateCommand cmd = new MunicaoCreateCommand(new ArmaDTO(municaoRequest.armaId()), municaoRequest.quantidade());
        municaoService.saveMunicao(cmd);

        return "redirect:/municoes";
    }

    @GetMapping("/{id}/edit")
    public String editarMunicaoShow(Principal principal, @PathVariable("id") UUID id, Model model) {
        System.out.println("editarMunicaoShow > principal = " + principal);

        Usuario usuario = usuarioService.findUsuarioByLogin(principal.getName()).orElseThrow(() -> UsuarioNotFoundException.of(principal.getName()));

        MunicaoDTO municao = municaoService.findMunicaoById(id).orElseThrow(() -> MunicaoNotFoundException.of(id));

        ArmaDTO armaDTO = new ArmaDTO(municao.arma().id(), municao.arma().calibre(), municao.arma().descricao());
        MunicaoUpdateRequest municaoRequest = new MunicaoUpdateRequest(municao.id(), armaDTO.id(), municao.quantidade());

        model.addAttribute("municaoRequest", municaoRequest);
        model.addAttribute("arma", armaDTO);

        return "municoes/municoes-edit";
    }

    @PutMapping("/{id}/edit")
    public String editarMunicao(Principal principal, @PathVariable("id") UUID id, @Valid @ModelAttribute("municaoRequest") MunicaoUpdateRequest municaoRequest) {
        System.out.println("editarMunicao > principal = " + principal);
        System.out.println("editarMunicao > municaoRequest = " + municaoRequest);

//        if (!id.equals(armaRequest.id()))
//            throw new ArmaInvalidDataException(id);

//        ArmaDTO arma = armaService.findArmaById(id).orElseThrow(() -> ArmaNotFoundException.of(id));
        MunicaoUpdateCommand cmd = new MunicaoUpdateCommand(municaoRequest.id(), new ArmaDTO(municaoRequest.armaId()), municaoRequest.quantidade());
        municaoService.updateMunicao(cmd);

        return "redirect:/municoes";
    }
}
