package com.nowensoft.administration.controllers.generic;

import java.io.Serializable;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nowensoft.administration.models.BaseEntity;

/**
 * Interfaz base para controladores que manejan entidades de tipo T,
 * asegurando que solo se utilicen modelos que extienden de BaseEntity.
 * Esta interfaz define métodos comunes para las operaciones de CRUD
 * en los controladores de la aplicación.
 * 
 * @param <T>  El tipo de la entidad, debe extender de BaseEntity.
 * @param <ID> El tipo del identificador único de la entidad, debe ser
 *             Serializable.
 * 
 * @author dumar
 */
public interface BaseController<T extends BaseEntity, ID extends Serializable> {

    /**
     * Método para listar todas las entidades.
     * 
     * @param model el objeto Model para pasar datos a la vista.
     * @return el nombre de la vista que muestra la lista.
     */
    String listar(Model model);

    /**
     * Muestra el formulario para crear una nueva entidad.
     * 
     * @param model el objeto Model para pasar datos a la vista.
     * @return el nombre de la vista del formulario de creación.
     */
    String mostrarFormularioCrear(Model model);

    /**
     * Guarda una nueva entidad.
     * 
     * @param entity             la entidad que se va a guardar.
     * @param redirectAttributes objeto para pasar mensajes de redirección.
     * @return una redirección a la lista de entidades.
     */
    String guardar(T entity, RedirectAttributes redirectAttributes);

    /**
     * Muestra el formulario de edición de una entidad existente.
     * 
     * @param id                 el ID de la entidad.
     * @param model              el objeto Model para pasar datos a la vista.
     * @param redirectAttributes
     * @return el nombre de la vista del formulario de edición.
     */
    String mostrarFormularioEditar(ID id, Model model, RedirectAttributes redirectAttributes);

    /**
     * Actualiza una entidad existente.
     * 
     * @param entity             la entidad que se va a actualizar.
     * @param redirectAttributes objeto para pasar mensajes de redirección.
     * @return una redirección a la lista de entidades.
     */
    String actualizar(ID id, T entity, RedirectAttributes redirectAttributes);

    /**
     * Elimina una entidad por su ID.
     * 
     * @param id                 el ID de la entidad.
     * @param redirectAttributes objeto para pasar mensajes de redirección.
     * @return una redirección a la lista de entidades.
     */
    String eliminar(ID id, RedirectAttributes redirectAttributes);
}