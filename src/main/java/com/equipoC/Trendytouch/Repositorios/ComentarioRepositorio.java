/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.equipoC.Trendytouch.Repositorios;

import com.equipoC.Trendytouch.Entidades.Comentario;
import com.equipoC.Trendytouch.Entidades.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Asus
 */

@Repository
public interface ComentarioRepositorio extends JpaRepository<Comentario, String> {
    
    @Query("SELECT c FROM Comentario c WHERE c.usuario.id = :idUsuario")
    public List<Comentario> buscarPorUsuario(@Param("idUsuario") String idUsuario);
    
    @Query("SELECT c FROM Comentario c WHERE c.alta LIKE false")
    public List<Comentario> buscarReportados();
    
}
