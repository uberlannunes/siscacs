package dev.uberlan.siscacs.api.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/treinamentos")
public class TreinamentoController {

    @GetMapping
    public String consultarArmas() {
        return "treinamentos/treinamentos-home";
    }

    @GetMapping("/new")
    public String cadastrarArma(Model model) {
        return "treinamentos/treinamentos-new";
    }
}
