package dev.uberlan.siscacs.api.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/armas")
public class ArmaController {

    @GetMapping
    public String consultarArmas() {
        return "armas/armas-home";
    }

    @GetMapping("/new")
    public String cadastrarArma(Model model) {
        return "armas/armas-new";
    }
}
