package com.nowensoft.administration.controllers.generic;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nowensoft.administration.models.BaseEntity;
import com.nowensoft.administration.services.generic.BaseServicioImpl;

/**
 * Clase abstracta que implementa la interfaz BaseController, proporcionando una
 * implementación base para controladores que manejan entidades de tipo T. Esta
 * clase utiliza un servicio de tipo S para realizar operaciones CRUD sobre la
 * entidad.
 *
 * @param <T> El tipo de la entidad, debe extender de BaseEntity.
 * @param <S> El tipo del servicio, debe extender de BaseServicioImpl.
 *
 *
 * @author dumar
 */
public abstract class BaseControllerImpl<T extends BaseEntity, S extends BaseServicioImpl<T, Long>>
        implements BaseController<T, Long> {

    /**
     *
     */
    @Autowired
    protected S service;

    public S getService() {
        return service;
    }

    public void setService(S service) {
        this.service = service;
    }

    /**
     * Método abstracto que debe ser implementado para devolver el nombre de la
     * entidad.
     *
     * @return El nombre de la entidad (por ejemplo, "permiso").
     */
    protected abstract String getEntityName();

    /**
     * Método abstracto que debe ser implementado para devolver la ruta base del
     * controlador.
     *
     * @return La ruta base (por ejemplo, "/admin/permisos").
     */
    protected abstract String getBaseUrl();
    protected abstract String getRedirectUrl();

    protected abstract String getCurrentPage();

    /**
     * Método abstracto que debe ser implementado para crear una nueva instancia
     * de la entidad.
     *
     * @return Una nueva instancia de la entidad.
     */
    protected abstract T createNewEntity();

    @Override
    @GetMapping
    /**
     * Lista todas las entidades de tipo T.
     *
     * @param model El objeto Model para pasar datos a la vista.
     * @return El nombre de la vista que muestra la lista de entidades.
     */
    public String listar(Model model) {
        List<T> entities = service.findAll();
        model.addAttribute("currentPage", getCurrentPage());
        model.addAttribute(getEntityName() + "s", entities);
        return getBaseUrl() + "/list";
    }

    @Override
    @GetMapping("/crear")
    /**
     * Muestra el formulario para crear una nueva entidad de tipo T.
     *
     * @param model El objeto Model para pasar datos a la vista.
     * @return El nombre de la vista del formulario de creación.
     */
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("currentPage", getCurrentPage());
        model.addAttribute(getEntityName(), createNewEntity());
        return getBaseUrl() + "/form";
    }

    @Override
    @PostMapping("/guardar")
    /**
     * Guarda una nueva entidad de tipo T.
     *
     * @param entity             La entidad que se va a guardar.
     * @param redirectAttributes Objeto para pasar mensajes de redirección.
     * @return Una redirección a la lista de entidades.
     */
    public String guardar(@ModelAttribute T entity, RedirectAttributes redirectAttributes) {
        try {
            service.save(entity);
            redirectAttributes.addFlashAttribute("mensaje", getEntityName() + " creado exitosamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje",
                    "Error al crear " + getEntityName() + ": " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }
        return "redirect:" + getRedirectUrl();
    }

    @Override
    @GetMapping("/editar/{id}")
    /**
     * Muestra el formulario de edición de una entidad existente de tipo T.
     *
     * @param id                 El ID de la entidad a editar.
     * @param model              El objeto Model para pasar datos a la vista.
     * @param redirectAttributes Objeto para pasar mensajes de redirección.
     * @return El nombre de la vista del formulario de edición o redirección a
     *         la lista.
     */
    public String mostrarFormularioEditar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<T> entityOpt = service.findById(id);

        if (entityOpt.isPresent()) {
            model.addAttribute("currentPage", getCurrentPage());
            model.addAttribute(getEntityName(), entityOpt.get());
            return getBaseUrl() + "/edit";
        } else {
            redirectAttributes.addFlashAttribute("error", getEntityName() + " no encontrado.");
            return "redirect:" + getRedirectUrl();
        }
    }

    @Override
    @PostMapping("/actualizar/{id}")
    /**
     * Actualiza una entidad existente de tipo T.
     *
     * @param entity             La entidad que se va a actualizar.
     * @param redirectAttributes Objeto para pasar mensajes de redirección.
     * @return Una redirección a la lista de entidades.
     */
    public String actualizar(@PathVariable Long id, @ModelAttribute T entity, RedirectAttributes redirectAttributes) {
        try {
            service.update(entity, id);
            redirectAttributes.addFlashAttribute("mensaje", getEntityName() + " actualizado exitosamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje",
                    "Error al actualizar " + getEntityName() + ": " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }
        return "redirect:" + getRedirectUrl();
    }

    @Override
    @GetMapping("/eliminar/{id}")
    /**
     * Elimina una entidad de tipo T por su ID.
     *
     * @param id                 El ID de la entidad a eliminar.
     * @param redirectAttributes Objeto para pasar mensajes de redirección.
     * @return Una redirección a la lista de entidades.
     */
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Optional<T> entityOpt = service.findById(id);
        if (entityOpt.isPresent()) {
            service.deleteById(id);
            redirectAttributes.addFlashAttribute("mensaje", getEntityName() + " eliminado exitosamente.");
        } else {
            redirectAttributes.addFlashAttribute("error", getEntityName() + " no encontrado.");
        }
        return "redirect:" + getRedirectUrl();
    }
}
