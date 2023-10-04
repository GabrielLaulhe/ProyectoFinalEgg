package com.equipoC.Trendytouch.Repositorios;

import com.equipoC.Trendytouch.Entidades.Publicacion;
import com.equipoC.Trendytouch.Entidades.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicacionRepositorio extends JpaRepository<Publicacion, String> {

    @Query("SELECT p FROM Publicacion p WHERE p.categoria = :categoria")
    public Publicacion buscarPorCategoria(@Param("categoria") String categoria);

    @Query("SELECT p FROM Publicacion p WHERE p.usuario = :usuario")
    public List<Publicacion> buscarUsuario(@Param("usuario") Usuario usuario);

    @Query("SELECT DISTINCT p FROM Publicacion p JOIN p.reportes r WHERE r.id = :id")
    public Publicacion buscarPublicacionPorReporteId(@Param("id") String id);

    @Query("SELECT p FROM Publicacion p ORDER BY (SIZE(p.comentarios) + SIZE(p.megusta)) DESC")
    public List<Publicacion> publicacionesConMasInteracciones();

    @Query("SELECT p FROM Publicacion p WHERE DATEDIFF(CURRENT_DATE, p.fechaPublicacion) <= 7 ORDER BY (SIZE(p.comentarios) + SIZE(p.megusta)) DESC")
    public List<Publicacion> publicacionesXInteraccionesSemanales();
}
