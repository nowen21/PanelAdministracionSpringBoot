package com.nowensoft.administration.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nowensoft.administration.repositories.admin.PermisoRepository;
import com.nowensoft.administration.repositories.admin.RoleRepository;
import com.nowensoft.administration.repositories.admin.RoleRutaRepository;
import com.nowensoft.administration.repositories.admin.UsuarioRepository;

@Controller
@RequestMapping("/administration/admin")
public class AdminController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermisoRepository permisoRepository;

    @Autowired
    private RoleRutaRepository roleRutaRepository;

    @GetMapping
    public String mostrarPanelAdministracion(Model model) {
        model.addAttribute("currentPage", "admin");
        return "/admin/index";
    }

    @GetMapping("/dashboard")
    public String mostrarDashboard(Model model) {
        model.addAttribute("totalUsuarios", usuarioRepository.count());
        model.addAttribute("totalRoles", roleRepository.count());
        model.addAttribute("totalPermisos", permisoRepository.count());
        model.addAttribute("totalRutas", roleRutaRepository.count());
        model.addAttribute("currentPage", "admin");
        return "admin/dashboard";
    }
}
