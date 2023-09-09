package com.equipoC.Trendytouch.Repositorios;

import com.equipoC.Trendytouch.Entidades.Reporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReporteRepositorio extends JpaRepository<Reporte, String>{
    
}