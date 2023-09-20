package com.equipoC.Trendytouch.Controladores;

import com.equipoC.Trendytouch.Entidades.Publicacion;
import com.equipoC.Trendytouch.Entidades.Usuario;
import com.equipoC.Trendytouch.Servicios.PublicacionServicio;
import com.equipoC.Trendytouch.Servicios.UsuarioServicio;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Asus
 */
@Controller
@RequestMapping("/")
public class PortalControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private PublicacionServicio publicacionServicio;

    @GetMapping("/")
    public String index(ModelMap modelo) {
        List<Publicacion> publicaciones = publicacionServicio.listarPublicacionesMegustas();
        modelo.addAttribute("publicaciones", publicaciones);

        return "index.html";
    }

    @GetMapping("/registrar") //localhost:8080
    public String registrar() {

        return "usuario_registro.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String apellido,
            @RequestParam String email, @RequestParam String password,
            String password2, @RequestParam String nombreUsuario,
            @RequestParam String pregunta, @RequestParam String respuesta,
            ModelMap modelo, MultipartFile archivo) {

        try {
            usuarioServicio.registrar(archivo, nombre, apellido, email,
                    nombreUsuario, password, password2, pregunta, respuesta);

            modelo.put("exito", "El usuario se registro correctamente.");
            return "login.html";
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("email", email);
            modelo.put("nombreUsuario", nombreUsuario);
            modelo.put("pregunta", pregunta);
            modelo.put("respuesta", respuesta);
            return "usuario_registro.html";
        }
    }

    @GetMapping("/loguear") //localhost:8080
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {
        if (error != null) {
            modelo.put("error", "Usuario o contraseña inválido.");
        }
        return "login.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_DISENADOR')")
    @GetMapping("/inicio")
    public String inicio(HttpSession session, ModelMap modelo) {

        Usuario logueado = (Usuario) session.getAttribute("usuariosession");

        if (logueado.getRol().toString().equalsIgnoreCase("admin")) {

            return "redirect:/admin/dashboard";
        }
        List<Publicacion> publicaciones = publicacionServicio.listarPublicaciones();
        modelo.addAttribute("publicaciones", publicaciones);

        return "inicio.html";
    }

<<<<<<< HEAD
    //Perfil y modificar perfil en controlador usuario /usuario/perfil
=======
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_DISENADOR')")
    @GetMapping("/perfil")
    public String perfil(ModelMap modelo, HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        modelo.put("usuario", usuario);

        return "usuario_modificar.html"; //hacer el html
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_DISENADOR')")
    @PostMapping("/perfil/{id}")
    public String actualizar(@PathVariable String id, @RequestParam String nombre, @RequestParam String apellido,
             @RequestParam String email, @RequestParam String nombreUsuario, @RequestParam String password, String password2,
            ModelMap modelo) {

        try {
            usuarioServicio.actualizar(id, nombre, apellido, email, nombreUsuario, password, password2);
            modelo.put("exito", "Usuario actualizado correctamente");
            return "inicio.html";

        } catch (MyException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);

            return "inicio.html";

        }

    }
    
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_DISENADOR')")
    @PostMapping("/cambiarFoto")
    public String actualizarFoto(HttpSession session,MultipartFile archivo) throws MyException{
        
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        
        usuarioServicio.cambiarFoto(archivo, logueado.getId());
        
        return "inicio.html";
    }
    
>>>>>>> 94f6492103e38e031199dc3adc58634a67430cf1
}
