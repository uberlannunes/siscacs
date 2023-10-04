package dev.uberlan.siscacs.api;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/municoes")
public class MunicaoController {

    @GetMapping
    public String consultarMunicoes() {
        return "municoes/municoes-home";
    }    

    @GetMapping("/new")
    public String cadastrarMunicao(Model model) {
        return "municoes/municoes-new";
    }
}
