package com.equipoC.Trendytouch.Controladores;

import com.equipoC.Trendytouch.Entidades.Usuario;
import com.equipoC.Trendytouch.Errores.MyException;
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
@RequestMapping("/usuario")
public class UsuarioControlador {

    @Autowired
    UsuarioServicio usuarioServicio;

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_DISENADOR')")
    @GetMapping("/perfil")
    public String perfil(ModelMap modelo, HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        modelo.put("usuario", usuario);

        return "perfil.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_DISENADOR')")
    @GetMapping("/actualizar")
    public String actualizar(ModelMap modelo, HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuariosession");
        modelo.put("usuario", usuario);

        return "usuario_modificar.html";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable String id, @RequestParam String nombre, @RequestParam String apellido,
            @RequestParam String email, @RequestParam String nombreUsuario, @RequestParam String password, String password2,
            ModelMap modelo) {

        try {                       
            usuarioServicio.actualizar(id, nombre, apellido, email, nombreUsuario, password, password2);            
            modelo.put("exito", "Usuario actualizado correctamente");
            return "redirect:/inicio";

        } catch (MyException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);

            return "usuario_modificar.html";

        }
        
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_DISENADOR')")
    @GetMapping
    public String reportar() {
        return "reporte_registro.html";
    }
}