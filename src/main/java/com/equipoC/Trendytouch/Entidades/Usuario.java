package com.equipoC.Trendytouch.Entidades;

import com.equipoC.Trendytouch.Enums.Categoria;
import com.equipoC.Trendytouch.Enums.Rol;
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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Usuario {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String nombre;
    private String apellido;
    private String email;
    private String nombreUsuario;
    private String password;
    private String pregunta;
    private String respuesta;
    private boolean alta;
    private Rol rol;
    
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Temporal(TemporalType.DATE)
    private Date fechaBaja;
    @Temporal(TemporalType.DATE)
    private Date ultimaVez;

    @OneToOne
    private Imagen imagen;
    
    @OneToMany
    private List<Reporte> reportes;
    

}
