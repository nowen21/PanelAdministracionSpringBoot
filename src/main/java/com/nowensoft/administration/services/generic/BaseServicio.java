/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nowensoft.administration.services.generic;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import com.nowensoft.administration.models.BaseEntity;

/**
 * Interfaz Base para servicios genéricos que manejan entidades de tipo T,
 * las cuales extienden de BaseEntity, y claves de tipo ID.
 * Proporciona métodos CRUD comunes para ser reutilizados en las
 * implementaciones de servicios.
 *
 * @author dumar
 * @param <T>  El tipo de la entidad, debe extender de BaseEntity.
 * @param <ID> El tipo del identificador único de la entidad, debe ser
 *             Serializable
 */
public interface BaseServicio<T extends BaseEntity, ID extends Serializable> {
    /**
     * Obtiene una lista de todas las entidades de tipo T almacenadas.
     *
     * @return Una lista de entidades T.
     */
    List<T> findAll();

    /**
     * Busca y devuelve una entidad de tipo T por su identificador.
     *
     * @param id El identificador de la entidad que se busca.
     * @return Un objeto Optional que contiene la entidad si se encuentra,
     *         o vacío si no se encuentra.
     */
    Optional<T> findById(ID id);

    /**
     * Guarda una nueva entidad en la base de datos.
     *
     * @param entity La entidad de tipo T que se desea guardar.
     * @return La entidad guardada, incluyendo cualquier modificación realizada
     *         por el framework (por ejemplo, generación de un ID).
     */
    T save(T entity);

    /**
     * Actualiza una entidad existente en la base de datos.
     *
     * @param entity             La entidad de tipo T con los nuevos valores a
     *                           actualizar.
     * @param id                 El identificador de la entidad que se va a
     *                           actualizar.
     * @param redirectAttributes
     * @return La entidad actualizada si se encuentra y modifica, o una excepción si
     *         no existe.
     */
    T update(T entity, ID id);

    /**
     * Elimina una entidad de la base de datos por su identificador.
     *
     * @param id El identificador de la entidad que se desea eliminar.
     */
    void deleteById(ID id);
}
