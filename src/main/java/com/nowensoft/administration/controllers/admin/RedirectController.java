package com.nowensoft.administration.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedirectController {
    @GetMapping("/")
    public String redirectToSpringBoot() {
        // Redirige a /spring-boot
        return "redirect:/administration";
    }
}
