package com.equipoC.Trendytouch.Controladores;

import com.equipoC.Trendytouch.Entidades.Reporte;
import com.equipoC.Trendytouch.Entidades.Usuario;
import com.equipoC.Trendytouch.Errores.MyException;
import com.equipoC.Trendytouch.Servicios.ComentarioServicio;
import com.equipoC.Trendytouch.Servicios.PublicacionServicio;
import com.equipoC.Trendytouch.Servicios.ReporteServicio;
import com.equipoC.Trendytouch.Servicios.UsuarioServicio;
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
    @Autowired
    private PublicacionServicio publicacionServicio;
    @Autowired
    private ComentarioServicio comentarioServicio;
    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/crear")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_DISENADOR')")
    public String crear() {
        return "reporte_registro.html";
    }

    @PostMapping("/crear")
    public String crear(@RequestParam String categoria, @RequestParam(required = false) String contenido,
            String tipo, HttpSession session, ModelMap modelo) {
        try {
            Usuario usuario = (Usuario) session.getAttribute("usuariosession");
            reporteServicio.crear(contenido, usuario, categoria, tipo);
            modelo.put("exito", "El reporte ha sido enviado con exito.");
            return "redirect:/inicio";
        } catch (MyException ex) {
            modelo.put("error", ex.getMessage());
            return "reporte_registro.html";
        }
    }

    //ver reporte
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

    //editar estado de reporte(aceptar o rechazar)
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

    //muestra los reportes del propio usuario    
    @GetMapping("/usuario")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_DISENADOR')")
    public String reportesPorUsuario(HttpSession session, ModelMap modelo) {
        modelo.put("reportes", reporteServicio.listarReportesPorEmisor((Usuario) session.getAttribute("usuariosession")));
        return "reportesListaUser.html";
    }

    //Busca una publicacion por id de reporte.
    @GetMapping("/publicacion/{id}")
    public String publicaciondeReporte(@PathVariable("id") String id, ModelMap modelo) {
        modelo.addAttribute("publicacion", publicacionServicio.publicacionporReporte(id));
        return "tarjeta_Publicacion.html";
    }

    //Busca un comentario por id de reporte.
    @GetMapping("/comentario/{id}")
    public String comentariodeReporte(@PathVariable("id") String id, ModelMap modelo) {
        modelo.addAttribute("comentario", comentarioServicio.comentarioporReporte(id));
        return "Comentario.html";
    }

    //Busca un Usuario por id de reporte.
    @GetMapping("/usuario/{id}")
    public String usuariodeReporte(@PathVariable("id") String id, ModelMap modelo) {
        Usuario publicador = usuarioServicio.usuarioporReporte(id);
        modelo.addAttribute("publicador", publicador);
        modelo.addAttribute("publicaciones", publicacionServicio.buscarPorUsuario(publicador));

        return "perfil.html";
    }

    @PostMapping("/buscar")
    public String buscarReportesEmisor(String consulta, ModelMap modelo) throws MyException {
        modelo.addAttribute("reportes", reporteServicio.busquedadeReportesporNombreUsuario(consulta));
        return "reportesLista.html";
    }
}
