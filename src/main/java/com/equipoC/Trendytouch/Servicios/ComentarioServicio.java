/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.equipoC.Trendytouch.Servicios;

import com.equipoC.Trendytouch.Entidades.Comentario;
import com.equipoC.Trendytouch.Entidades.Reporte;
import com.equipoC.Trendytouch.Entidades.Usuario;
import com.equipoC.Trendytouch.Errores.MyException;
import com.equipoC.Trendytouch.Repositorios.ComentarioRepositorio;
import com.equipoC.Trendytouch.Repositorios.PublicacionRepositorio;
import com.equipoC.Trendytouch.Repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Asus
 */
// --------   A REVISAR   -----
@Service
public class ComentarioServicio {

    @Autowired
    private ComentarioRepositorio comentarioRepo;

    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private ReporteServicio reporteServicio;

    @Transactional
    public Comentario registrarComentario(String contenido, String idUsuario) throws MyException {

        validar(contenido, idUsuario);

        Comentario comentario = new Comentario();

        Date fechaComentario = new Date();

        comentario.setFechaComentario(fechaComentario);
        comentario.setContenido(contenido);
        comentario.setAlta(Boolean.TRUE);

        Usuario usuario = new Usuario();
        usuario = usuarioServicio.getOne(idUsuario);
        comentario.setUsuario(usuario);

        comentarioRepo.save(comentario);

        return comentario;
    }

    @Transactional
    public void editarComentario(String id, String contenido) throws MyException {

        validarContenido(contenido);

        Optional<Comentario> respuesta = comentarioRepo.findById(id);

        if (respuesta.isPresent()) {
            Comentario comentario = respuesta.get();

            comentario.setContenido(contenido);

            comentarioRepo.save(comentario);
        }
    }

    @Transactional
    public void eliminarComentarioPorId(String id) throws MyException {

        validarId(id);

        Optional<Comentario> respuesta = comentarioRepo.findById(id);
        if (respuesta.isPresent()) {
            comentarioRepo.deleteById(id);
        }

    }

    public Comentario comentarioPorId(String id) {
        return comentarioRepo.getOne(id);
    }

    public void validar(String contenido, String idUsuario) throws MyException {

        validarId(idUsuario);

        validarContenido(contenido);
    }

    public void validarId(String id) throws MyException {

        if (id.isEmpty() || id == null) {
            throw new MyException("El id esta vacio");
        }
    }

    public void validarContenido(String contenido) throws MyException {
        if (contenido.isEmpty() || contenido == null) {
            throw new MyException("Debe ingresar un comentario para guardar");
        }
    }

    public List<Comentario> listarComentariosPorUsuario(String idUsuario) {
        List<Comentario> comentarios = new ArrayList();

        comentarios = (List<Comentario>) comentarioRepo.buscarPorUsuario(idUsuario);
        return comentarios;
    }

    public List<Comentario> listarComentariosReportados() {
        List<Comentario> comentarios = comentarioRepo.buscarReportados();
        return comentarios;
    }

    public Comentario getone(String id) {
        Comentario comentario = comentarioRepo.getOne(id);
        return comentario;
    }

    @Transactional
    public void reportarComentario(String id,Usuario emisor, String categoria, String contenido, String tipo) throws MyException {
        Comentario comentario = getone(id);
        List<Reporte> reportes = comentario.getReportes();
        Reporte reporte = reporteServicio.crear(contenido, emisor, categoria, tipo);
        reportes.add(reporte);
        comentario.setReportes(reportes);
        comentarioRepo.save(comentario);
    }
    
    public Comentario comentarioporReporte(String id){
        Comentario comentario = comentarioRepo.buscarComentarioPorReporteId(id);
        return comentario;
    }

}
