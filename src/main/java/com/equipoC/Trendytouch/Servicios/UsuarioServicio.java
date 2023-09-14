package com.equipoC.Trendytouch.Servicios;

import com.equipoC.Trendytouch.Entidades.Imagen;
import com.equipoC.Trendytouch.Entidades.Usuario;
import com.equipoC.Trendytouch.Enums.Rol;
import com.equipoC.Trendytouch.Errores.MyException;
import com.equipoC.Trendytouch.Repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    UsuarioRepositorio usuariorepo;

    @Autowired
    ImagenServicio imagenservicio;

    @Transactional
    public void registrar(MultipartFile archivo, String nombre, String apellido, String email,
            String nombreUsuario, String password, String password2, String pregunta, String respuesta) throws MyException {

        validar(nombre, apellido, email, nombreUsuario, password, password2);
        validar2(email, nombreUsuario);

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setEmail(email);
        usuario.setNombreUsuario(nombreUsuario);
        usuario.setPregunta(pregunta);
        usuario.setRespuesta(respuesta);
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        usuario.setRol(Rol.USER);
        usuario.setAlta(true);
        Date fechaRegistro = new Date();
        usuario.setFechaRegistro(fechaRegistro);
        Imagen imagen = imagenservicio.guardar(archivo);
        usuario.setImagen(imagen);
        usuariorepo.save(usuario);

    }

    public void validar2(String email, String nombreUsuario) throws MyException {
        if (usuariorepo.buscarPorEmail(email) != null) {
            throw new MyException("ERROR, EMAIL YA SE ENCUENTRA EN USO!");
        }
        if (usuariorepo.buscarPorNombreUsuario(nombreUsuario) != null) {
            throw new MyException("ERROR, EL NOMBRE DE USUARIO YA SE ENCUENTRA EN USO!");
        }

    }

    private void validar(String nombre, String apellido, String email, String nombreUsuario,
            String password, String password2) throws MyException {

        if (nombre.isEmpty() || nombre == null) {
            throw new MyException("EL nombre no puede estar vacio.");
        }
        if (apellido.isEmpty() || apellido == null) {
            throw new MyException("EL apellido no puede estar vacio.");
        }
        if (email.isEmpty() || email == null) {
            throw new MyException("EL email no puede estar vacio.");
        }
        if (nombreUsuario.isEmpty() || nombreUsuario == null) {
            throw new MyException("EL nombreUsuario no puede estar vacio.");
        }
        if (password.isEmpty() || password == null || password.length() <= 5) {
            throw new MyException("La contraseña no puede estar vacia y tiene que tener mas de 5 caracteres.");
        }
        if (!password.equals(password2)) {
            throw new MyException("Las contraseñas no coiciden.");
        }

    }

    @Transactional
    public void cambiarFoto(MultipartFile archivo, String idUsuario) throws MyException {

        Optional<Usuario> respuesta = usuariorepo.findById(idUsuario);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();

            String idImagen = null;

            if (usuario.getImagen() != null) {
                idImagen = usuario.getImagen().getId();
            }

            Imagen imagen = imagenservicio.actualizar(archivo, idImagen);

            usuario.setImagen(imagen);

            usuariorepo.save(usuario);

        }

    }

    @Transactional
    public void actualizar(String idUsuario, String nombre, String apellido, String email,
            String nombreUsuario, String password, String password2) throws MyException {

        validar(nombre, apellido, email, nombreUsuario, password, password2);

        Optional<Usuario> respuesta = usuariorepo.findById(idUsuario);
        if (respuesta.isPresent()) {

            Usuario usuario = respuesta.get();
            usuario.setNombre(nombre);
            usuario.setApellido(apellido);
            usuario.setNombreUsuario(nombreUsuario);
            usuario.setEmail(email);

            usuario.setPassword(new BCryptPasswordEncoder().encode(password));

            usuariorepo.save(usuario);
        }

    }

    public Usuario getOne(String id) {
        return usuariorepo.getOne(id);
    }

    @Transactional(readOnly = true)
    public List<Usuario> listarUsuarios() {

        List<Usuario> usuarios = usuariorepo.findAll();

        return usuarios;
    }

    @Transactional
    public void cambiarRol(String id, String rol) {
        Optional<Usuario> respuesta = usuariorepo.findById(id);

        rol = rol.toUpperCase();

        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            usuario.setRol(Rol.valueOf(rol));
        }
    }

    @Transactional
    public void eliminar(String id) throws MyException {

        try {
            Usuario usuario = usuariorepo.getById(id);
            usuariorepo.delete(usuario);
        } catch (Exception e) {
            throw new MyException(e.getMessage());
        }

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Usuario usuario = usuariorepo.buscarPorEmail(email);

        if (usuario != null) {
            List<GrantedAuthority> permisos = new ArrayList();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());
            permisos.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession();
            session.setAttribute("usuariosession", usuario);

            return new User(usuario.getEmail(), usuario.getPassword(), permisos);
        } else {
            return null;
        }

    }

    public UserDetails loadUserByUsernameUsuario(String nombreUsuario) throws UsernameNotFoundException {

        Usuario usuario = usuariorepo.buscarPorNombreUsuario(nombreUsuario);

        if (usuario != null) {
            List<GrantedAuthority> permisos = new ArrayList();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());
            permisos.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession();
            session.setAttribute("usuariosession", usuario);

            return new User(usuario.getEmail(), usuario.getPassword(), permisos);
        } else {
            throw new UsernameNotFoundException("Usuario no encontrado: " + nombreUsuario);
        }
    }

}
