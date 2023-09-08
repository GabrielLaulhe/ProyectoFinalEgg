package com.equipoC.Trendytouch.Repositorios;

import com.equipoC.Trendytouch.Entidades.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicacionRepositorio extends JpaRepository<Publicacion, String> {
    /*
    @Query("SELECT p FROM publicacion p WHERE p.categoria = :categoria")
    public Publicacion buscarPorCategoria();
    */
}