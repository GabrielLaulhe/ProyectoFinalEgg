package com.equipoC.Trendytouch.Controladores;

import com.equipoC.Trendytouch.Entidades.Usuario;
import com.equipoC.Trendytouch.Servicios.PublicacionServicio;
import com.equipoC.Trendytouch.Servicios.UsuarioServicio;
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

@Controller
@RequestMapping("/")
public class PortalControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private PublicacionServicio publicacionServicio;

    @GetMapping("/")
    public String index(ModelMap modelo) {
        modelo.addAttribute("publicaciones", publicacionServicio.listarPublicacionesMegustas());
        return "index.html";
    }

    //registro
    @GetMapping("/registrar") //localhost:8080
    public String registrar() {
        return "usuario_registro.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String apellido,
            @RequestParam String email, @RequestParam String password,
            String password2, @RequestParam String nombreUsuario,
            String pregunta, String respuesta,
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

    //login
    @GetMapping("/loguear") //localhost:8080
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {
        if (error != null) {
            modelo.put("error", "Usuario o contraseña inválido.");
        }
        return "login.html";
    }

    //redireccion según tipo de usuario que se logueo
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_DISENADOR')")
    @GetMapping("/inicio")
    public String inicio(HttpSession session, ModelMap modelo) {

        Usuario logueado = (Usuario) session.getAttribute("usuariosession");

        if (logueado.getRol().toString().equalsIgnoreCase("admin")) {

            return "redirect:/admin/dashboard";
        }
        modelo.addAttribute("publicaciones", publicacionServicio.listarPublicaciones());

        return "inicio.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_DISENADOR')")
    @GetMapping("/populares")
    public String indexPopular(ModelMap modelo) {
        modelo.addAttribute("publicaciones", publicacionServicio.publicacionesMasInteracciones());
        return "inicio.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_DISENADOR')")
    @GetMapping("/semanales")
    public String indexSemanal(ModelMap modelo) {
        modelo.addAttribute("publicaciones", publicacionServicio.publicacionesMasInteraccionesSemanales());
        return "inicio.html";
    }
}
