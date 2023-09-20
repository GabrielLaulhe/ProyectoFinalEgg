package com.equipoC.Trendytouch.Controladores;

import com.equipoC.Trendytouch.Entidades.Reporte;
import com.equipoC.Trendytouch.Entidades.Usuario;
import com.equipoC.Trendytouch.Errores.MyException;
import com.equipoC.Trendytouch.Servicios.ReporteServicio;
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

@Controller
@RequestMapping("/reporte")
public class ReporteControlador {

    @Autowired
    private ReporteServicio reporteServicio;

    @GetMapping("/crear")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_DISENADOR')")
    public String crear() {
        return "reporte_registro.html";
    }

    @PostMapping("/crear")
    public String crear(@RequestParam String categoria, @RequestParam(required = false) String contenido,
            HttpSession session, ModelMap modelo) {
        try {
            Usuario usuario = (Usuario) session.getAttribute("usuariosession");
            reporteServicio.crear(contenido, usuario, categoria);
            modelo.put("exito", "El reporte ha sido enviado con exito.");
            return "redirect:/inicio";
        } catch (MyException ex) {
            modelo.put("error", ex.getMessage());
            return "reporte_registro.html";
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_DISENADOR')")
    public String reporte(@PathVariable String id, ModelMap modelo) {
        try {
            Reporte reporte = reporteServicio.getOne(id);
            modelo.put("reporte", reporte);
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            return "redirect:/inicio";
        }
        return "reporte.html";
    }

    @GetMapping("/editar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editarEstado(@PathVariable String id, ModelMap modelo) {
        try {
            Reporte reporte = reporteServicio.getOne(id);
            modelo.put("reporte", reporte);
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            return "redirect:/inicio";
        }
        return "reporte.html";
    }

    @PostMapping("/editar/{id}")
    public String editarEstado(@PathVariable String id, @RequestParam String estado, ModelMap modelo) {
        try {
            reporteServicio.cambiarEstado(id, estado);
            modelo.put("exito", "El reporte se ha actualizado con exito.");
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            return "reporte.html";
        }
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/usuario")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_DISENADOR')")
    public String reportesPorUsuario(HttpSession session, ModelMap modelo) {

        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        List<Reporte> reportes = reporteServicio.listarReportesPorEmisor(usuario);
        modelo.put("reportes", reportes);

        return "reportesListaUser.html";
    }

    //lista de reportes para el admin completa en admin controlador /admin/reportes
}
