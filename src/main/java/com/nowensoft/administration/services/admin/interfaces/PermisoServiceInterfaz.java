/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.nowensoft.administration.services.admin.interfaces;

import com.nowensoft.administration.models.admin.Permiso;
import com.nowensoft.administration.services.generic.BaseServicio;

/**
 * Interfaz que define los métodos específicos para el servicio de Permiso.
 * Extiende de BaseServicio para heredar métodos comunes de gestión de
 * entidades.
 * 
 * @author dumar
 */
public interface PermisoServiceInterfaz extends BaseServicio<Permiso, Long> {
  // Aquí puedes agregar métodos específicos para PermisoService, por ejemplo:

  /**
   * Método para buscar permisos por un atributo específico.
   *
   * @param atributo El atributo por el que se desea filtrar.
   * @return Lista de permisos que coinciden con el criterio.
   */
  // List<Permiso> findByAtributoEspecifico(String atributo);

  // Otros métodos específicos pueden ser añadidos aquí
}
