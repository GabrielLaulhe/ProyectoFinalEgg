/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.equipoC.Trendytouch.Servicios;

import com.equipoC.Trendytouch.Entidades.Imagen;
import com.equipoC.Trendytouch.excepciones.MiException;
import com.equipoC.Trendytouch.repositorio.ImagenRepositorio;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Asus
 */
@Service
public class ServicioImagen {

    @Autowired
    private ImagenRepositorio imagenRepositorio;

    
    public Imagen guardar(MultipartFile archivo) throws MiException {

        if (archivo.getContentType().contains("octet")) {
            try {
                Imagen imagen = new Imagen();
                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());
                return imagenRepositorio.save(imagen);

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }
    
    public Imagen actualizar (MultipartFile archivo, String id) throws MiException {
        
        if (archivo.getContentType().contains("octet")) {
            try {
                Imagen imagen = new Imagen();
                
                if(id != null) {
                    Optional<Imagen> respuesta = imagenRepositorio.findById(id);
                    if (respuesta.isPresent()) {
                        imagen = respuesta.get();
                    }
                }
                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());
                return imagenRepositorio.save(imagen);

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
        
    }
    
    
}
