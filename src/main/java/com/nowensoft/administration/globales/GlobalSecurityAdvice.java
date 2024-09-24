package com.nowensoft.administration.globales;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.nowensoft.administration.services.admin.UsuarioService;

/**
 * Controlador de asesoramiento global que se utiliza para aplicar configuración
 * o lógica común de seguridad a los controladores del proyecto.
 *
 * La anotación @ControllerAdvice permite centralizar excepciones o lógica
 * compartida, en este caso relacionado con la autenticación y los permisos de
 * usuarios.
 * 
 *  @author Ing. José Dúmar Jiménez Ruíz (nowen21@gmail.com)
 */
@ControllerAdvice
public class GlobalSecurityAdvice {

    // Servicio de gestión de usuarios inyectado mediante @Autowired
    @Autowired
    private UsuarioService UsuarioService;

    /**
     * Método que agrega el servicio de usuario al modelo como atributo. Este
     * atributo se puede acceder en las vistas que utilizan el modelo.
     *
     * @return el servicio de usuario que será accesible en las vistas.
     */
    @ModelAttribute("UsuarioService")
    public UsuarioService tienePermiso() {
        return UsuarioService;
    }

    /**
     * Método que agrega la autenticación actual al modelo. Si el usuario está
     * autenticado, la información de autenticación se añade como atributo al modelo
     * y estará disponible en las vistas.
     *
     * @param model el modelo de la vista donde se agregará el atributo de
     *              autenticación.
     */
    @ModelAttribute
    public void addAuthenticationToModel(Model model) {
        // Obtener la autenticación actual desde el contexto de seguridad.
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // Si el usuario está autenticado, se añade al modelo.
        if (auth != null && auth.isAuthenticated()) {
            model.addAttribute("auth", auth);
        }
    }
}
