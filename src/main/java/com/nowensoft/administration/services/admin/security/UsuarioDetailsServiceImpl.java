package com.nowensoft.administration.services.admin.security;



import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nowensoft.administration.models.admin.Usuario;
import com.nowensoft.administration.repositories.admin.UsuarioRepository;

@Service
public class UsuarioDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        // // Cargar roles y permisos como authorities
        // Set<SimpleGrantedAuthority> authorities = usuario.getRoles().stream()
        //         .flatMap(rol -> {
        //             // Agregar el rol
        //             Set<SimpleGrantedAuthority> roleAuthorities = rol.getPermisos().stream()
        //                     .map(permiso -> new SimpleGrantedAuthority(permiso.getNombre()))
        //                     .collect(Collectors.toSet());
        //             roleAuthorities.add(new SimpleGrantedAuthority("ROLE_" + rol.getNombre()));
        //             return roleAuthorities.stream();
        //         })
        //         .collect(Collectors.toSet());

        // boolean isEnabled = false;

        // if (usuario.getEstado().getId() == 1L) {
        //     isEnabled = true;
        // }

        // return new User(usuario.getEmail(), usuario.getPassword(), isEnabled, true, true, true,
        //         authorities);

        return usuario;
    }
}
