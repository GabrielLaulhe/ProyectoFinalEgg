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
    private UsuarioServicio usuarioServicio;

    @Autowired
    private ComentarioServicio comentarioServicio;

    @PreAuthorize("hasAnyRole('ROLE_DISENADOR', 'ROLE_ADMIN')")
    @GetMapping("/crear") //localhost:8080
    public String crear() {
        return "publicacion_registro.html";
    }

    //crear publicacion
    @PostMapping("/crear")
    public String crear(@RequestParam(required = false) String descripcion, HttpSession session,
            @RequestParam List<MultipartFile> archivos, @RequestParam String categoria, ModelMap modelo) {
        try {
            publicacionServicio.registrarPublicacion(descripcion, (Usuario) session.getAttribute("usuariosession"), categoria, archivos);
            modelo.put("exito", "Plublicacion ok");
            return "redirect:/inicio";

        } catch (MyException e) {

            modelo.put("error", e.getMessage());
            return "publicacion_registro.html";

        }

    }

    //publicaciones por usuario
    @GetMapping("/usuario")
    public String PublicacionesdeUsuario(HttpSession session, ModelMap modelo) {
        modelo.addAttribute("publicaciones", publicacionServicio.buscarPorUsuario((Usuario) session.getAttribute("usuariosession")));
        return "inicio.html";
    }

    //eliminar publicaciones(funcion admin)
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
        modelo.addAttribute("publicacion", publicacionServicio.getOne(id));
        return "reporte_form.html";
    }

    @PostMapping("/reportarPublicacion")
    public String reportar(String idReportado, HttpSession session,
            @RequestParam String categoria, @RequestParam(required = false) String contenido, ModelMap modelo, String tipo) {
        try {
            publicacionServicio.reportarPublicacion(idReportado, (Usuario) session.getAttribute("usuariosession"), contenido, categoria, tipo);
            return "redirect:/inicio";
        } catch (MyException e) {
            modelo.put("error", e.getMessage());
            return "reporte_form.html";
        }

    }

    //reportar comentario
    @GetMapping("/reportarComentario/{id}")
    public String guardarReporteComentario(@PathVariable("id") String id, ModelMap modelo) {
        modelo.addAttribute("idComentario", id);
        modelo.addAttribute("comentario", comentarioServicio.getone(id));
        return "reporte_comentario.html";

    }

    @PostMapping("/guardarReporte")
    public String guardarReporteComentario(@RequestParam String categoria, @RequestParam(required = false) String contenido,
            String idReportado, String tipo, HttpSession session, ModelMap modelo) throws MyException {
        try {
            comentarioServicio.reportarComentario(idReportado, (Usuario) session.getAttribute("usuariosession"), categoria, contenido, tipo);
            return "redirect:/inicio";

        } catch (MyException ex) {
            modelo.put("error", ex.getMessage());
            return "reporte_comentario.html";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_DISENADOR', 'ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping("/comentar")
    public String comentar(@RequestParam String id, ModelMap modelo, String contenido, HttpSession session) throws MyException {
        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        try {
            publicacionServicio.comentar(id, comentarioServicio.registrarComentario(contenido, usuario.getId()));
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            return "redirect:/inicio";
        }
        return "redirect:/inicio";
    }
    //mostrar categoria de la publicacion

    @PreAuthorize("hasAnyRole('ROLE_DISENADOR', 'ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/categorias/{categoria}")
    public String categoriaPublicacion(ModelMap modelo, @PathVariable("categoria") String categoria) {
        modelo.addAttribute("publicaciones", publicacionServicio.publicacionesxCategoria(categoria));
        return "inicio.html";
    }

    @GetMapping("/like/{id}/{idP}")
    public String likePublicacion(@PathVariable("id") String id, @PathVariable("idP") String idP) {
        try {
            Usuario usuarioLike = usuarioServicio.getOne(id);
            Publicacion publicacion = publicacionServicio.getOne(idP);
            if(usuarioLike != null & publicacion != null){
                publicacionServicio.registrarLikesDePublicacion(usuarioLike, publicacion);
            }
            return "redirect:/inicio";
        } catch (Exception e) {
            System.out.println(e);
            return "redirect:/inicio";
        }
    }
}
