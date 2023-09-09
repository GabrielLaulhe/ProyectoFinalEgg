/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.equipoC.Trendytouch.Servicios;

import com.equipoC.Trendytouch.Entidades.Imagen;
import com.equipoC.Trendytouch.Errores.MyException;
import com.equipoC.Trendytouch.Repositorios.ImagenRepositorio;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Asus
 */

@Service
public class ImagenServicio {

    @Autowired
    private ImagenRepositorio ir;

    @Transactional
    public Imagen guardar(MultipartFile archivo) throws MyException  {

        archivo = validar(archivo);

        if (archivo != null) {
            try {
                Imagen imagen = new Imagen();

                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());

                return ir.save(imagen);

            } catch (Exception  e) {

                throw new MyException ("No se pudo guardar la imagen");
            }
        }

        return null;
    }

    @Transactional
    public Imagen actualizar(MultipartFile archivo, String id) throws MyException  {

        archivo = validar(archivo);

        if (archivo != null) {

            try {

                Imagen imagen = new Imagen();

                if (id != null) {

                    Optional<Imagen> respuesta = ir.findById(id);

                    if (respuesta.isPresent()) {
                        imagen = respuesta.get();
                    }
                }

                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());

                return ir.save(imagen);

            } catch (Exception  e) {
                throw new MyException ("No se pudo actualizar la imagen");
            }
        }

        if (id != null) {
            ir.deleteById(id);
        }

        return null;
    }

    public Imagen getOne(String id) {
        return ir.getOne(id);
    }

    public MultipartFile validar(MultipartFile archivo) {
        if (archivo != null && archivo.getContentType().contains("octet")) {
            return null;
        }


        return archivo;
    }
} 


    
    

