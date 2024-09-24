package com.nowensoft.administration.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {
    @GetMapping("/administration/acceso-denegado")
    public String accessDenied() {
        return "admin/acceso-denegado"; // Retorna la vista 'access-denied.html' o 'access-denied.jsp'
    }
}
