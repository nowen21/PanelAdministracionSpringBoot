/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nowensoft.administration.repositories.generic;

import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.nowensoft.administration.models.BaseEntity;

/**
 * Interfaz base para repositorios que manejan entidades de tipo E,
 * asegurando que solo se utilicen modelos que extienden de BaseEntity.
 * 
 * Esta interfaz hereda de JpaRepository, proporcionando métodos CRUD
 * comunes y permitiendo la reutilización del código en repositorios
 * específicos para diferentes entidades.
 * 
 * @param <E>  El tipo de la entidad, debe extender de BaseEntity.
 * @param <ID> El tipo del identificador único de la entidad, debe ser
 *             Serializable.
 * 
 * @author dumar
 */
@NoRepositoryBean
public interface BaseRepository<E extends BaseEntity, ID extends Serializable> extends JpaRepository<E, ID> {
    // Este repositorio no necesita métodos adicionales,
    // ya que hereda todos los métodos de JpaRepository.
}
