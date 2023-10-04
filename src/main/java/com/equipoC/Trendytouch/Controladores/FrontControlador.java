package com.equipoC.Trendytouch.Controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/front")
public class FrontControlador {

    @GetMapping("/index") //localhost:8080/editorial/registrar
    public String registrar() {
        return "index.html";
    }
}
