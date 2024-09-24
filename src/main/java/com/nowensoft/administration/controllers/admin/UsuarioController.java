package com.nowensoft.administration.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nowensoft.administration.controllers.generic.BaseControllerImpl;
import com.nowensoft.administration.models.admin.Usuario;
import com.nowensoft.administration.services.admin.UsuarioService;

@Controller
@RequestMapping("/administration/admin/usuarios")
public class UsuarioController extends BaseControllerImpl<Usuario, UsuarioService> {

    @Override
    protected String getEntityName() {
        return "usuario";
    }

    @Override
    protected String getCurrentPage() {
        return "usuarios";
    }

    @Override
    protected String getBaseUrl() {
        return "/admin/usuarios"; // Definir la ruta base sin el prefijo "/admin"
    }

    @Override
    protected String getRedirectUrl() {
        return "/administration/admin/usuarios"; // Definir la ruta base sin el prefijo "/admin"
    }

    @Override
    protected Usuario createNewEntity() {
        return new Usuario();
    }
}
