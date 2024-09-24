package com.nowensoft.administration.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/administration/login")
    public String login(Model model) {
        model.addAttribute("currentPage", "");
        return "admin/login"; // Devuelve la vista login.html
    }
}
