package com.equipoC.Trendytouch.Entidades;


import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Publicacion {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
    private String descripcion;
   
    @OneToMany
    private List<Reporte> reportes;
    
    @ManyToOne
    private Usuario usuario;
    
    @OneToMany
    private List<Usuario> megusta;
    
    @OneToMany
    private List<Imagen> fotos;
    
    @OneToMany
    private List<Comentario> comentarios;
    
    @Enumerated(EnumType.STRING)
    private Categoria categoria;
    
    @Temporal(TemporalType.DATE)
    private Date fechaPublicacion;
    
}
