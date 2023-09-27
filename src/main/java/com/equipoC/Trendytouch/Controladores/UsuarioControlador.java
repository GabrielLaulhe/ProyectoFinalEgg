package com.equipoC.Trendytouch.Controladores;

import com.equipoC.Trendytouch.Entidades.Reporte;
import com.equipoC.Trendytouch.Entidades.Usuario;
import com.equipoC.Trendytouch.Errores.MyException;
import com.equipoC.Trendytouch.Servicios.ReporteServicio;
import com.equipoC.Trendytouch.Servicios.UsuarioServicio;
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

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_DISENADOR')")
    @GetMapping("/perfil")
    public String perfil(ModelMap modelo, HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        modelo.put("usuario", usuario);

        return "perfil.html";
    }

    //editar perfil
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

    //reportar usuario
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DISENADOR')")
    @GetMapping("/reportar")
    public String reportar() {
        return "reporte_registro.html";
    }

    @PostMapping("/reportar")
    public String reportar(@PathVariable String idReportado, HttpSession session,
            @RequestParam String categoria, @RequestParam(required = false) String contenido, ModelMap modelo) {
        try {
            Usuario emisor = (Usuario) session.getAttribute("usuariosession");
            Reporte reporte = reporteServicio.crear(contenido, emisor, categoria);
            Usuario reportado = usuarioServicio.getOne(idReportado);
            List<Reporte> reportes = reportado.getReportes();
            reportes.add(reporte);
            reportado.setReportes(reportes);
        } catch (MyException e) {
            modelo.put("error", e.getMessage());
            return "reporte_registro.html";
        }
        return "redirect:/inicio";
    }

    //editar foto
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_DISENADOR')")
    @PostMapping("/cambiarFoto")
    public String actualizarFoto(HttpSession session, MultipartFile archivo) throws MyException {
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        usuarioServicio.cambiarFoto(archivo, logueado.getId());
        return "inicio.html";
    }
}
