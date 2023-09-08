package com.equipoC.Trendytouch.Servicios;

import com.equipoC.Trendytouch.Entidades.Reporte;
import com.equipoC.Trendytouch.Entidades.Usuario;
import com.equipoC.Trendytouch.Errores.MyException;
import com.equipoC.Trendytouch.Repositorios.ReporteRepositorio;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReporteServicio {
    
    @Autowired
    private ReporteRepositorio reporteRepositorio;
    
    @Transactional
    public void crear(String contenido, Usuario emisor, String categoria) throws MyException{
        validar(emisor, categoria);
        
        Reporte reporte = new Reporte();        
        
        reporte.setContenido(contenido);
        reporte.setEmisor(emisor);
        reporte.setCategoria(categoria);

        reporteRepositorio.save(reporte);        
    }
    
    public void eliminar(String id) throws MyException{
        if (id == null){
            throw new MyException("El id es nulo o no existe.");
        }
        Optional<Reporte> respuesta = reporteRepositorio.findById(id);        
        if (respuesta.isPresent()){
            Reporte reporte = respuesta.get();
            reporteRepositorio.delete(reporte);
        }        
    }
    
    public Reporte getOne(String id){
        return reporteRepositorio.getOne(id);
    }

    private void validar(Usuario emisor, String categoria) throws MyException {
        if (emisor == null){
            throw new MyException("Debe loguearse para realizar un reporte.");
        }
        
        if (categoria == null || categoria.isEmpty()){
            throw new MyException("Debe seleccionar una categoría.");
        }
    }
}
