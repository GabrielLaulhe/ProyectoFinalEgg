package com.equipoC.Trendytouch.Controladores;

import com.equipoC.Trendytouch.Entidades.Usuario;
import com.equipoC.Trendytouch.Errores.MyException;
import com.equipoC.Trendytouch.Servicios.PublicacionServicio;
import com.equipoC.Trendytouch.Servicios.ReporteServicio;
import com.equipoC.Trendytouch.Servicios.UsuarioServicio;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/usuario")
public class UsuarioControlador {

    @Autowired
    UsuarioServicio usuarioServicio;
    @Autowired
    ReporteServicio reporteServicio;
    @Autowired
    PublicacionServicio publicacionServicio;

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_DISENADOR')")
    @GetMapping("/perfil")
    public String perfil(ModelMap modelo, HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        modelo.put("usuario", usuario);

        return "perfil.html";
    }

    // editar perfil
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_DISENADOR')")
    @GetMapping("/actualizar")
    public String actualizar(ModelMap modelo, HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        modelo.put("usuario", usuario);

        return "usuario_modificar.html";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable String id, @RequestParam String nombre, @RequestParam String apellido,
            @RequestParam String email, @RequestParam String nombreUsuario, @RequestParam String password,
            String password2,
            ModelMap modelo, HttpSession session) {

        try {
            usuarioServicio.actualizar(id, nombre, apellido, email, nombreUsuario, password, password2);
            modelo.put("exito", "Usuario actualizado correctamente");
            return "redirect:/inicio";

        } catch (MyException ex) {
            Usuario usuario = (Usuario) session.getAttribute("usuariosession");
            modelo.put("usuario", usuario);
            modelo.put("error", ex.getMessage());

            return "usuario_modificar.html";

        }

    }

    // reportar usuario
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DISENADOR', 'ROLE_USER')")
    @GetMapping("/reportar/{id}")
    public String reportar(@PathVariable("id") String id, ModelMap modelo) {
        modelo.addAttribute("id", id);
        modelo.addAttribute("usuario", usuarioServicio.getOne(id));
        return "reporte_usuario.html";

    }

    @PostMapping("/reportar")
    public String reportar(String idReportado, HttpSession session,
            @RequestParam String categoria, @RequestParam(required = false) String contenido, String tipo,
            ModelMap modelo) {
        try {
            usuarioServicio.reportarUsuario(idReportado, (Usuario) session.getAttribute("usuariosession"), categoria,
                    contenido, tipo);
        } catch (MyException e) {
            modelo.put("error", e.getMessage());
            return "reporte_usuario.html";
        }
        return "redirect:/inicio";
    }

    // editar foto
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_DISENADOR')")
    @PostMapping("/cambiarFoto")
    public String actualizarFoto(HttpSession session, MultipartFile archivo) throws MyException {
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        usuarioServicio.cambiarFoto(archivo, logueado.getId());
        return "redirect:/inicio";
    }

    @GetMapping("/{id}")
    public String perfil(@PathVariable("id") String id, HttpSession session, ModelMap modelo) {
        try {
            Usuario publicador = usuarioServicio.getOne(id);
            modelo.addAttribute("publicador", publicador);
            modelo.addAttribute("publicaciones", publicacionServicio.buscarPorUsuario(publicador));

            return "perfil.html";
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            return "redirect:/inicio";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_DISENADOR')")
    @GetMapping("/preguntas")
    public String preguntasDeSeguridad() {
        return "preguntas.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_DISENADOR')")
    @PostMapping("/preguntas/{id}")
    public String preguntasDeSeguridad(@PathVariable("id") String id, @RequestParam String pregunta,
            String respuesta, ModelMap modelo) throws MyException {
        try {
            usuarioServicio.preguntaSeguridad(id, pregunta, respuesta);
        } catch (MyException e) {
            modelo.put("error", e.getMessage());
        }
        return "redirect:/inicio";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_DISENADOR')")
    @GetMapping("/buscar")
    public String buscarUsuario(@RequestParam(required = false) String consulta, ModelMap modelo) {
        List<Usuario> usuario = new ArrayList<>();
        try {
            if (consulta.isEmpty()) {
                modelo.addAttribute("usuarios", usuario);
            } else {
                modelo.addAttribute("usuarios", usuarioServicio.busquedadeUsuarios(consulta));
            }
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
        }
        return "buscarUsuario.html";
    }

}
