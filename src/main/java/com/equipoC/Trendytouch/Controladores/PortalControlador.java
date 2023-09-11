/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.equipoC.Trendytouch.Controladores;

import com.equipoC.Trendytouch.Servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Asus
 */

@Controller
@RequestMapping("/")
public class PortalControlador {
    
    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @GetMapping("/")
    public String index() {
        //List<Publicacion> publicaciones = publicacionServicio.listarPublicaciones();
        //modelo.addAttribute("publicaciones", publicaciones);
        return "index.html";
    }
    
    @GetMapping("/registrar") //localhost:8080
    public String registrar() {

        return "registrar.html";
    }
    
    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String apellido, 
                            @RequestParam String email, @RequestParam String password, 
                            String password2, @RequestParam String nombreUsuario, 
                            @RequestParam String pregunta, @RequestParam String respuesta, 
                            ModelMap modelo, MultipartFile archivo) {

        try {
            usuarioServicio.registrar(archivo, nombre,  apellido, email,
                                        nombreUsuario, password, password2, pregunta, respuesta);
            
            modelo.put("exito", "El usuario se registro correctamente");
            return "index.html";
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);
            modelo.put("nombreUsuario", nombreUsuario);
            modelo.put("pregunta", pregunta);
            modelo.put("respuesta", respuesta);
            return "registrar.html";
        }
    }

    @GetMapping("/login") //localhost:8080
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {
        if (error != null) {
            modelo.put("error", "usuario o contrasena invalido");
        }
        return "login.html";
    }
}
