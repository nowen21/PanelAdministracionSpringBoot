package com.nowensoft.administration.controllers.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nowensoft.administration.controllers.generic.BaseControllerImpl;
import com.nowensoft.administration.datatable.DataTableResponse;
import com.nowensoft.administration.dtos.PermisoDTO;
import com.nowensoft.administration.models.admin.Permiso;
import com.nowensoft.administration.models.admin.Role;
import com.nowensoft.administration.services.admin.PermisoService;
import com.nowensoft.administration.services.admin.RoleService;

@Controller
@RequestMapping("/administration/admin/roles")
public class RoleController extends BaseControllerImpl<Role, RoleService> {
    @Autowired
    PermisoService permisoService;

    @Override
    protected String getEntityName() {
        return "role";
    }

    @Override
    protected String getCurrentPage() {
        return "roles";
    }

    @Override
    protected String getBaseUrl() {
        return "/admin/roles"; // Definir la ruta base sin el prefijo "/admin"
    }

    @Override
    protected String getRedirectUrl() {
        return "/administration/admin/roles"; // Definir la ruta base sin el prefijo "/admin"
    }


    @Override
    protected Role createNewEntity() {
        return new Role();
    }

    @GetMapping("/permisos/{id}")

    public String permisos(Model model, @PathVariable Long id) {
        Role role = service.findById(id).get();
        model.addAttribute("currentPage", getCurrentPage());
        model.addAttribute("rol", role);
        model.addAttribute("permisos", permisoService.findAll());

        return getBaseUrl() + "/permisos/list";
    }

    /**
     * listar permisos asignados al rol
     * 
     * @param id
     * @return
     */
    @SuppressWarnings("rawtypes")
    @GetMapping("/rolpermisos/{id}")
    @ResponseBody
    public DataTableResponse rolpermisos(@PathVariable Long id) {
        List<PermisoDTO> roleOptional = service.findPermisosByRoleId(id);
        // Crear una lista vacía por defecto
        List<PermisoDTO> permisosList = new ArrayList<>();

        if (roleOptional != null) {
            permisosList = roleOptional;
        }

        return new DataTableResponse<>(permisosList);
    }

    @GetMapping("/epermiso/{roleId}/{permisoId}")
    @ResponseBody
    public ResponseEntity<?> eliminarPermiso(@PathVariable Long roleId, @PathVariable Long permisoId) {
        // Buscar el rol por su ID
        try {
            Optional<Role> roleOptional = service.findById(roleId);

            if (roleOptional.isPresent()) {
                Role role = roleOptional.get();

                // Buscar el permiso a eliminar
                Optional<Permiso> permisoOptional = role.getPermisos()
                        .stream()
                        .filter(permiso -> permiso.getId().equals(permisoId))
                        .findFirst();

                if (permisoOptional.isPresent()) {
                    Permiso permisoAEliminar = permisoOptional.get();
                    // Eliminar el permiso del rol
                    role.getPermisos().remove(permisoAEliminar);

                    // Guardar los cambios
                    service.save(role);
                    return ResponseEntity.ok().body("{\"message\": \"Permiso eliminado con éxito\"}");
                } else {
                    // El permiso no existe en el rol
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\": \"Permiso no encontrado\"}");
                }
            } else {
                // El rol no existe
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\": \"Rol no encontrado\"}");
            }
        } catch (Exception e) {
            // Manejo de excepciones genéricas
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error interno del servidor\"}");
        }
    }

    /**
     * listar permisos para asignar al rol
     * 
     * @param model
     * @param id
     * @return
     */
    @SuppressWarnings("rawtypes")
    @GetMapping("/apipermisos/{rolId}")
    @ResponseBody
    public DataTableResponse apipermisos(Model model, @PathVariable Long rolId) {

        // Crear una lista vacía por defecto
        List<Permiso> permisosList = new ArrayList<>();

        try {
            // Obtener todos los permisos
            List<Permiso> todosPermisos = permisoService.findAll();

            // Obtener el rol por su ID
            Optional<Role> roleOptional = service.findById(rolId);
            if (roleOptional.isPresent()) {
                Role role = roleOptional.get();

                // Obtener los permisos asignados al rol
                List<Permiso> permisosAsignados = new ArrayList<>(role.getPermisos());

                // Filtrar permisos no asignados al rol
                permisosList = todosPermisos.stream()
                        .filter(permiso -> !permisosAsignados.contains(permiso))
                        .collect(Collectors.toList());
            } else {
                // El rol no existe
                return new DataTableResponse<>(permisosList); // Devolver lista vacía si el rol no se encuentra
            }
        } catch (Exception e) {
            // Manejo de excepciones genéricas
            e.printStackTrace();
            // Devolver lista vacía en caso de error
            return new DataTableResponse<>(permisosList);
        }

        // Devolver la lista filtrada
        return new DataTableResponse<>(permisosList);
    }

    @GetMapping("/apermiso/{roleId}/{permisoId}")
    @ResponseBody
    public ResponseEntity<?> agregarPermiso(@PathVariable Long roleId, @PathVariable Long permisoId) {
        try {
            // Buscar el rol y el permiso
            Role role = service.findById(roleId).get();
            Optional<Permiso> permiso = permisoService.findById(permisoId);

            // Agregar el permiso al rol
            role.getPermisos().add(permiso.get());

            // Guardar los cambios
            service.save(role);
            return ResponseEntity.ok().body("{\"message\": \"Permiso agregado con éxito\"}");

        } catch (Exception e) {
            // Manejo de excepciones genéricas
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error interno del servidor\"}");
        }
    }

}
