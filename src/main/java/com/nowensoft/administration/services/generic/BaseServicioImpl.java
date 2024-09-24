
package com.nowensoft.administration.services.generic;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import com.nowensoft.administration.models.BaseEntity;

import com.nowensoft.administration.repositories.generic.BaseRepository;

/**
 * Clase abstracta que implementa la interfaz BaseServicio, proporcionando una
 * implementación base para servicios genéricos que manejan entidades de tipo T
 * y claves de tipo ID.
 * Esta clase utiliza un repositorio genérico BaseRepository para interactuar
 * con la base de datos.
 *
 * @param <T>  El tipo de la entidad, debe extender de BaseEntity.
 * @param <ID> El tipo del identificador único de la entidad, debe ser
 *             Serializable.
 * 
 * @author Ing. José Dúmar Jiménez Ruíz (nowen21@gmail.com)
 */
public abstract class BaseServicioImpl<T extends BaseEntity, ID extends Serializable> implements BaseServicio<T, ID> {

    // Repositorio genérico para manejar la entidad de tipo T y su identificador de
    // tipo ID.
    protected BaseRepository<T, ID> baseRepository;

    /**
     * Valida los campos únicos de la entidad antes de realizar una actualización.
     * Este método debe ser implementado para verificar si los campos únicos de la
     * entidad
     * (como un nombre de usuario, correo electrónico, etc.) ya están en uso por
     * otra entidad diferente a la que se está actualizando.
     *
     * @param entity La entidad que contiene los datos que se desean actualizar.
     * @param id     El identificador de la entidad que está siendo actualizada.
     * @throws ValidationException Si alguno de los campos únicos ya existe en otra
     *                             entidad.
     */
    protected abstract void validateUniqueFields(T entity, ID id);

    /**
     * Valida los campos únicos de la entidad antes de realizar una operación de
     * guardado.
     * Este método debe ser implementado para asegurarse de que los campos que
     * deberían ser únicos en la base de datos no se repitan al guardar una nueva
     * entidad.
     *
     * @param entity La entidad que se va a guardar.
     * @throws ValidationException Si alguno de los campos únicos ya existe en otra
     *                             entidad.
     */
    protected abstract void validateUniqueFieldsSave(T entity);

    /**
     * Actualiza los campos de una entidad existente con los valores de una nueva
     * entidad.
     * Este método debe ser implementado para transferir los valores de la entidad
     * de entrada a la entidad existente en la base de datos, asegurándose de que
     * solo los campos permitidos sean actualizados.
     *
     * @param existingEntity La entidad existente en la base de datos que se desea
     *                       actualizar.
     * @param entity         La entidad que contiene los nuevos valores.
     * @throws EntityNotFoundException Si la entidad existente no se encuentra.
     */
    protected abstract void updateEntityFields(T existingEntity, T entity);

    /**
     * Constructor que inyecta el repositorio genérico en el servicio.
     *
     * @param baseRepository El repositorio genérico que se utilizará para las
     *                       operaciones CRUD.
     */
    public BaseServicioImpl(BaseRepository<T, ID> baseRepository) {
        this.baseRepository = baseRepository;
    }

    /**
     * Obtiene todas las entidades de tipo T almacenadas en el repositorio.
     *
     * @return Una lista de todas las entidades de tipo T.
     */
    @Override
    @Transactional
    public List<T> findAll() {
        return baseRepository.findAll(); // Devuelve todas las entidades del repositorio.
    }

    /**
     * Busca y obtiene una entidad de tipo T utilizando su identificador único.
     *
     * @param id El identificador único de la entidad a buscar.
     * @return Un objeto Optional que contiene la entidad si se encuentra, o vacío
     *         si no.
     */
    @Override
    @Transactional
    public Optional<T> findById(ID id) {
        return baseRepository.findById(id); // Busca la entidad por su ID.
    }

    /**
     * Guarda una nueva entidad de tipo T en el repositorio.
     *
     * @param entity La entidad a ser guardada.
     * @return La entidad guardada, incluyendo cualquier modificación realizada
     *         por el framework, como la generación automática de identificadores.
     */
    @Override
    @Transactional
    public T save(T entity) {
        validateUniqueFieldsSave(entity);
        return baseRepository.save(entity); // Guarda la entidad en el repositorio.
    }

    /**
     * Actualiza una entidad existente en el repositorio utilizando su identificador
     * único.
     *
     * @param entity La entidad con los nuevos valores que serán actualizados.
     * @param id     El identificador único de la entidad a actualizar.
     * @return La entidad actualizada.
     */
    @Override
    @Transactional
    public T update(T entity, ID id) {
        Optional<T> optionalEntity = baseRepository.findById(id);
        if (optionalEntity.isPresent()) {
            T existingEntity = optionalEntity.get();
            // Validar unicidad utilizando el método privado
            validateUniqueFields(entity, id);
            updateEntityFields(existingEntity, entity);
            return baseRepository.save(existingEntity); // Actualiza la entidad existente.
        } else {
            throw new EntityNotFoundException("La entidad con ID " + id + " no existe.");
        }
    }

    /**
     * Elimina una entidad del repositorio utilizando su identificador único.
     *
     * @param id El identificador único de la entidad a eliminar.
     */
    @Override
    @Transactional
    public void deleteById(ID id) {
        if (baseRepository.existsById(id)) {
            baseRepository.deleteById(id); // Elimina la entidad si existe.
        } else {
            throw new EntityNotFoundException("La entidad con ID " + id + " no existe.");
        }
    }
}
