package com.nowensoft.administration.controllers;

import java.time.LocalDate;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/administration")
    public String index(Model model) {


        model.addAttribute("currentDate", LocalDate.now());
        // model.addAttribute("authentication", authentication);
        model.addAttribute("currentPage", "inicio");
        // Retorna la vista llamada "index"
        return "index";
    }
}
