package com.equipoC.Trendytouch.Controladores;

import com.equipoC.Trendytouch.Entidades.Usuario;
import com.equipoC.Trendytouch.Errores.MyException;
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

/**
 *
 * @author Asus
 */
@Controller
@RequestMapping("/admin")
public class AdminControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private PublicacionServicio publicacionServicio;
    @Autowired
    private ReporteServicio reporteServicio;

    @GetMapping("/dashboard")
    public String panelAdministrativo(ModelMap modelo, HttpSession session) {
        modelo.addAttribute("publicaciones", publicacionServicio.listarPublicacionesMegustas());
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        modelo.addAttribute("usuariolog", usuarioServicio.getOne(logueado.getId()));
        return "inicio.html";
    }

    // lista de todos los usuarios
    @GetMapping("/usuarios")
    public String listar(ModelMap modelo) {
        modelo.addAttribute("usuarios", usuarioServicio.listarUsuarios());
        return "usuariosLista.html"; // crear una vista
    }

    // lista de todos los reportes
    @GetMapping("/reportes")
    @PreAuthorize("hasRole('ADMIN')")
    public String listaReportes(ModelMap modelo) {

        try {
            modelo.put("reportes", reporteServicio.listarReportes());
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            return "redirect:/reporte/reportesLista";
        }
        return "reportesLista.html";
    }

    // boton para modificar el rol de un usuario
    @PostMapping("/modificarRol/{id}")
    public String cambiarRol(@RequestParam("id") String id, @RequestParam String rol) {
        usuarioServicio.cambiarRol(id, rol);
        return "redirect:/admin/usuarios";
    }

    // boton para modificar el estado de un usuario
    @GetMapping("/modificarEstado/{id}")
    public String cambiarEstado(@PathVariable("id") String id) {
        usuarioServicio.cambiarEstado(id);
        return "redirect:/admin/usuarios";
    }

    // botón para eliminar un usuario
    @PostMapping("/borrar/{id}")
    public String eliminar(@RequestParam("id") String id, ModelMap modelo) throws MyException {
        try {
            usuarioServicio.eliminar(id);
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
        }

        return "redirect:/admin/usuarios";
    }

    // busqueda en la lista de usuario por nombre
    @PostMapping("/buscar")
    public String buscarusuario(String consulta, ModelMap modelo) throws MyException {
        modelo.addAttribute("usuarios", usuarioServicio.busquedadeUsuarios(consulta));
        return "usuariosLista.html";
    }
}
