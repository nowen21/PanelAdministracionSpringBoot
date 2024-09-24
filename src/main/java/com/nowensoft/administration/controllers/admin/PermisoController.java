package com.nowensoft.administration.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nowensoft.administration.controllers.generic.BaseControllerImpl;
import com.nowensoft.administration.models.admin.Permiso;
import com.nowensoft.administration.services.admin.PermisoService;

/**
 *
 * @author dumar
 */
@Controller
@RequestMapping("/administration/admin/permisos")
public class PermisoController extends BaseControllerImpl<Permiso, PermisoService> {

    // Constructor que inyecta el servicio específico de permisos

    @Override
    protected String getEntityName() {
        return "permiso";
    }

    @Override
    protected String getCurrentPage() {
        return "permisos";
    }

    @Override
    protected String getBaseUrl() {
        return "/admin/permisos"; // Definir la ruta base sin el prefijo "/admin"
    }
    
    @Override
    protected String getRedirectUrl() {
        return "/administration/admin/permisos"; // Definir la ruta base sin el prefijo "/admin"
    }

    

    @Override
    protected Permiso createNewEntity() {
        return new Permiso();
    }

}
