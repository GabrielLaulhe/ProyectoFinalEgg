package com.equipoC.Trendytouch.Entidades;


import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class Publicacion {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String categoria;
    private String descripcion;
   
    @OneToOne
    private Reporte reportes;
    @OneToMany
    private List<Comentario> comentarios;
    @OneToMany
    private List<Imagen> imagenes;
    
    /* No se si sera necesario estos otros atributos
    @Temporal(TemporalType.DATE)
    private Date fechaPublicacion;
    @Temporal(TemporalType.DATE)
    private Date fechaRetiro;   
    */
}
