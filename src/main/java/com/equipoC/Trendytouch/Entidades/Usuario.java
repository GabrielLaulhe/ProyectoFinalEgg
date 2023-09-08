package com.equipoC.Trendytouch.Entidades;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
    private boolean diseñador;
    
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Temporal(TemporalType.DATE)
    private Date fechaBaja;
    @Temporal(TemporalType.DATE)
    private Date ultimaVez;

    @OneToOne
    private Imagen imagen;

}
