package dev.uberlan.siscacs.api.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(Exception e) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.addObject("tituloPagina", "Não foi possível processar a solicitação.");
        mav.addObject("mensagemErro", e.getMessage());

        mav.setViewName("erro");
        return mav;
    }
}
