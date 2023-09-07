package com.equipoC.Trendytouch.Entidades;

import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
    private String nombre;
    private String apellido;
    private String email;
    private String nombreusuario;
    private String password;
    private String pregunta;
    private String respuesta;
    private boolean alta;
    private boolean diseñador;
    private Date fecharegistro;
    private Date fechabaja;
    private Date ultimavez;
    private Imagen imagen;
    
}
