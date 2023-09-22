/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.equipoC.Trendytouch.Controladores;

import com.equipoC.Trendytouch.Entidades.Comentario;
import com.equipoC.Trendytouch.Entidades.Publicacion;
import com.equipoC.Trendytouch.Entidades.Reporte;
import com.equipoC.Trendytouch.Entidades.Usuario;
import com.equipoC.Trendytouch.Errores.MyException;
import com.equipoC.Trendytouch.Repositorios.PublicacionRepositorio;
import com.equipoC.Trendytouch.Servicios.ComentarioServicio;
import com.equipoC.Trendytouch.Servicios.PublicacionServicio;
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
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Facu
 */
@Controller
@RequestMapping("/publicacion")
public class PublicacionControlador {

    @Autowired
    private PublicacionServicio publicacionServicio;
    
    @Autowired
    private PublicacionRepositorio publicacionRepositorio;

    @Autowired
    private ReporteServicio reporteServicio;

    @Autowired
    private ComentarioServicio comentarioServicio;

    @PreAuthorize("hasAnyRole('ROLE_DISENADOR', 'ROLE_ADMIN')")
    @GetMapping("/crear") //localhost:8080
    public String crear() {
        return "publicacion_registro.html";
    }

    @PostMapping("/crear")
    public String crear(@RequestParam(required = false) String descripcion, HttpSession session,
            @RequestParam List<MultipartFile> archivos, @RequestParam String categoria, ModelMap modelo) {
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");

        try {
            publicacionServicio.registrarPublicacion(descripcion, usuario, categoria, archivos);
            modelo.put("exito", "Plublicacion ok");
            return "redirect:/inicio";

        } catch (MyException e) {

            modelo.put("error", e.getMessage());
            return "publicacion_registro.html";

        }

    }

    @GetMapping("/usuario")
    public String PublicacionesdeUsuario(HttpSession session, ModelMap modelo) {
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        List<Publicacion> publicaciones = publicacionServicio.buscarUsuario(usuario);
        modelo.addAttribute("publicaciones", publicaciones);
        return "inicio.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_DISENADOR', 'ROLE_ADMIN')")
    @PostMapping("/borrar/{id}")
    public String eliminar(@RequestParam("id") String id, ModelMap modelo) throws MyException {
        try {
            publicacionServicio.eliminarPublicacionPorId(id);
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
        }
        return "redirect:/";
    }

    // Reportar Publicacion
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DISENADOR', 'ROLE_USER')")
    @GetMapping("/reportar/{id}")
    public String reportar(@PathVariable("id") String id, ModelMap modelo) {
        modelo.addAttribute("id", id);
        return "reporte_form.html";
    }

    @PostMapping("/reportarPublicacion")
    public String reportarPublicacion(String idReportado, HttpSession session,
            @RequestParam String categoria, @RequestParam(required = false) String contenido, ModelMap modelo) {
        try {
            Usuario emisor = (Usuario) session.getAttribute("usuariosession");
            Reporte reporte = reporteServicio.crear(contenido, emisor, categoria);
            Publicacion reportado = publicacionServicio.getOne(idReportado);
            List<Reporte> reportes = reportado.getReportes();
            reportes.add(reporte);
            reportado.setReportes(reportes);
            publicacionRepositorio.save(reportado);
            return "redirect:/inicio";
        } catch (MyException e) {
            modelo.put("error", e.getMessage());
            return "reporte_form.html";
        }

    }

    @GetMapping("/reportarComentario/{id}")
    public String guardarReporteComentario(@PathVariable("id") String id, ModelMap modelo) {

        modelo.addAttribute("idComentario", id);

        return "reporte_form.html";

    }

    @PostMapping("/guardarReporte")
    public String guardarReporteComentario(@RequestParam String categoria, @RequestParam(required = false) String contenido,
            @RequestParam("idComentario") String id, HttpSession session, ModelMap modelo) throws MyException {
        try {

            Comentario comentario = comentarioServicio.comentarioPorId(id);
            Usuario emisor = (Usuario) session.getAttribute("usuariosession");
            Reporte reporte = reporteServicio.crear(contenido, emisor, categoria);

            List<Reporte> reportes = comentario.getReportes();
            reportes.add(reporte);
            comentario.setReportes(reportes);

            return "redirect:/publicacion";

        } catch (MyException ex) {
            modelo.put("error", ex.getMessage());
            return "reporte_form.html";
        }
    }
    
    @PreAuthorize("hasAnyRole('ROLE_DISENADOR', 'ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping("/comentar")
    public String comentar(@RequestParam String id,ModelMap modelo, String contenido, HttpSession session) throws MyException {
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        try {
            publicacionServicio.comentar(id, comentarioServicio.registrarComentario(contenido, usuario.getId()));
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            return "redirect:/inicio";
        }
        return "redirect:/inicio";
    }
}
