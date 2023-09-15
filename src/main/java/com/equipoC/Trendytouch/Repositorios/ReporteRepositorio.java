package com.equipoC.Trendytouch.Repositorios;

import com.equipoC.Trendytouch.Entidades.Reporte;
import com.equipoC.Trendytouch.Entidades.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReporteRepositorio extends JpaRepository<Reporte, String>{
    
    @Query("SELECT r FROM Reporte r WHERE r.emisor = :emisor")
    public List<Reporte> buscarReportePorEmisor(@Param("emisor") Usuario emisor);
}
