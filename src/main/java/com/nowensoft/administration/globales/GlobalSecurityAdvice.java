package com.nowensoft.administration.globales;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.nowensoft.administration.services.admin.UsuarioService;

@ControllerAdvice
public class GlobalSecurityAdvice {

 @Autowired
    private UsuarioService UsuarioService;

    @ModelAttribute("UsuarioService")
    public UsuarioService tienePermiso() {
        return UsuarioService;
    }



    @ModelAttribute
    public void addAuthenticationToModel(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            model.addAttribute("auth", auth);
        }
    }

}
