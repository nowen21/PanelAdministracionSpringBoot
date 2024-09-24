package com.nowensoft.administration.seeder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.nowensoft.administration.models.admin.Estado;
import com.nowensoft.administration.models.admin.Permiso;
import com.nowensoft.administration.models.admin.Role;
import com.nowensoft.administration.models.admin.RoleRuta;
import com.nowensoft.administration.models.admin.Ruta;
import com.nowensoft.administration.models.admin.Usuario;
import com.nowensoft.administration.repositories.admin.EstadoRepository;
import com.nowensoft.administration.repositories.admin.PermisoRepository;
import com.nowensoft.administration.repositories.admin.RoleRepository;
import com.nowensoft.administration.repositories.admin.RoleRutaRepository;
import com.nowensoft.administration.repositories.admin.RutaRepository;
import com.nowensoft.administration.repositories.admin.UsuarioRepository;

@Component
public class DataSeeder implements CommandLineRunner {
    @Autowired
    private UsuarioRepository usuarioRepository; // Repositorio que utilizarás para insertar los datos

    @Autowired
    EstadoRepository estadoRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    RutaRepository rutaRepository;

    @Autowired
    RoleRutaRepository rolerutaRepository;

    @Autowired
    PermisoRepository permisoRepository;

    @SuppressWarnings("unchecked")
    @Override
    public void run(String... args) throws Exception {

        if (estadoRepository.count() == 0) {
            Estado activo = new Estado();
            activo.setNombre("Acitvo");
            estadoRepository.save(activo);

            Estado inactivo = new Estado();
            inactivo.setNombre("Inacitvo");
            estadoRepository.save(inactivo);
        }
        Estado estado = estadoRepository.findById(1L).get();
        if (roleRepository.count() == 0) {
            Role admin = new Role();

            admin.setNombre("ADMINISTRADOR");
            admin.setEstado(estado);
            admin.setDescripcion("Administrador del sistema");
            roleRepository.save(admin);

            Role user = new Role();
            user.setNombre("USER");
            user.setEstado(estado);
            user.setDescripcion("Usuario regular");
            roleRepository.save(user);
        }

        // Verificar si ya existen datos
        if (usuarioRepository.count() == 0) {
            BCryptPasswordEncoder pass = new BCryptPasswordEncoder();
            // Insertar datos de prueba
            Usuario usuario = new Usuario();
            usuario.setPnombre("Admin");
            usuario.setSnombre("Admin");
            usuario.setPapellido("Admin");
            usuario.setSapellido("Admin");
            usuario.setDocumento("1111111111");
            usuario.setEmail("admin@admin.com");
            usuario.setSuperuser(1l);
            usuario.setPassword(pass.encode("admin123"));
            usuario.setEstado(estadoRepository.findById(1L).get());
            List<Role> roles = new ArrayList<>();

            roles.add(roleRepository.findById(1L).get());

            Set<Role> sroles = new HashSet<>(roles);
            usuario.setRoles(sroles);
            usuarioRepository.save(usuario);
        }
        Usuario usuario = usuarioRepository.findById(1L).get();
        Role role = roleRepository.findById(1L).get();

        if (rutaRepository.count() == 0) {

            guardaRuta(role, estado, usuario, "/administration/admin/**", "Panel de Administración");
            guardaRuta(role, estado, usuario, "/permisos/**", "Gestión permisos");
            guardaRuta(role, estado, usuario, "/usuarios/**", "Gestión usuarios");
            guardaRuta(role, estado, usuario, "/roles/**", "Gestión roles");
            // guardaRuta(role,estado, usuario,"/public/**","Acceso público");
            // guardaRuta(role,estado, usuario,"/","Acceso al index");
        }


        if (permisoRepository.count()==0) {
            List<Permiso> permisos=new ArrayList<>();
            permisos=crudPermiso(permisos, usuario,estado,"Usuario","USER");
            permisos=crudPermiso(permisos, usuario,estado,"Rol","ROLE");
            permisos=crudPermiso(permisos, usuario,estado,"Permiso","PERMISSION");
           
            Set<Permiso> spermisos = new HashSet<>(permisos);
            role.setPermisos(spermisos);
            roleRepository.save(role);
        }
    }

    private void guardaRuta(Role role, Estado estado, Usuario usuario, String url, String descr) {
        Ruta ruta = new Ruta();
        ruta.setUrl(url);
        ruta.setDescripcion(descr);
        ruta.setEstado(estado);
        ruta.setUsuaregi(usuario);
        ruta.setUsuamodi(usuario);
        ruta = rutaRepository.save(ruta);
        RoleRuta rr = new RoleRuta();
        rr.setEstado(estado);
        rr.setUsuamodi(usuario);
        rr.setUsuaregi(usuario);
        rr.setRuta(ruta);
        rr.setRole(role);
        rolerutaRepository.save(rr);
    }

    private Permiso insertarPermiso( Usuario usuario,Estado estado,String nombre,String codename){
        Permiso permiso =new Permiso();
        permiso.setUsuamodi(usuario);
        permiso.setUsuaregi(usuario);
        permiso.setCodename(codename);
        permiso.setEstado(estado);
        permiso.setNombre(nombre);
        return permisoRepository.save(permiso);
    }

    private List<Permiso> crudPermiso( List<Permiso> permisos, Usuario usuario,Estado estado,String nombre,String codename){
        permisos.add(insertarPermiso( usuario,estado,"Crear "+nombre,"CREATE_"+codename));
        permisos.add(insertarPermiso( usuario,estado,"Actulizar "+nombre,"UPDATE_"+codename));
        permisos.add(insertarPermiso( usuario,estado,"Eliminar "+nombre,"DELETE_"+codename));
        permisos.add(insertarPermiso( usuario,estado,"Ver "+nombre,"READ_"+codename));
        return permisos;
    }
}
