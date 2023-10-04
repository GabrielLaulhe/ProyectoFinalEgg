package com.equipoC.Trendytouch.Repositorios;

import com.equipoC.Trendytouch.Entidades.Comentario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ComentarioRepositorio extends JpaRepository<Comentario, String> {

    @Query("SELECT c FROM Comentario c WHERE c.usuario.id = :idUsuario")
    public List<Comentario> buscarPorUsuario(@Param("idUsuario") String idUsuario);

    @Query("SELECT c FROM Comentario c WHERE c.alta LIKE false")
    public List<Comentario> buscarReportados();

    @Query("SELECT DISTINCT c FROM Comentario c JOIN c.reportes r WHERE r.id = :id")
    public Comentario buscarComentarioPorReporteId(@Param("id") String id);
}
