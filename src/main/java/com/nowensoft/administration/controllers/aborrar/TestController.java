package com.nowensoft.administration.controllers.aborrar;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {
    @GetMapping("/administration/test-auth")
    @ResponseBody
    public String testAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return "Usuario autenticado: " + authentication;
        } else {
            return "No hay usuario autenticado";
        }
    }
}
