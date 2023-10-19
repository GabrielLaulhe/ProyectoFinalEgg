package com.equipoC.Trendytouch.Repositorios;

import com.equipoC.Trendytouch.Entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {

    @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    public Usuario buscarPorEmail(@Param("email") String email);

    @Query("SELECT u FROM Usuario u WHERE u.nombreUsuario = :nombreUsuario")
    public Usuario buscarPorNombreUsuario(@Param("nombreUsuario") String nombreUsuario);

    @Query("SELECT DISTINCT u FROM Usuario u JOIN u.reportes r WHERE r.id = :id")
    public Usuario buscarUsuarioPorReporteId(@Param("id") String id);
}
