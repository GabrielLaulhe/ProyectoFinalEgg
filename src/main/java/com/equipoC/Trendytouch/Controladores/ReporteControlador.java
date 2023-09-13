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
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public String crear() {
        return "crear_reporte.html";
    }

    @PostMapping
    public String crear(@RequestParam String categoria, @RequestParam(required = false) String contenido,
            HttpSession session, ModelMap modelo) {
        try {
            Usuario usuario = (Usuario) session.getAttribute("usuariosession");
            reporteServicio.crear(contenido, usuario, categoria);
            modelo.put("exito", "El reporte ha sido enviado con exito.");
            return "redirect:/inicio";
        } catch (MyException ex) {
            modelo.put("error", ex.getMessage());
            return "crear_reporte.html";
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
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

    @GetMapping("/lista")
    @PreAuthorize("hasRole('ADMIN')")
    public String listaReportes(ModelMap modelo) {
        
        try {
            List<Reporte> reportes = reporteServicio.listarReportes();
            modelo.put("reportes", reportes);            
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            return "redirect:/inicio";            
        }
        return "lista_reportes.html";
    }
}
