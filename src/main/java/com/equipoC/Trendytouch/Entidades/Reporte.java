package com.equipoC.Trendytouch.Entidades;

import com.equipoC.Trendytouch.Enums.EstadoReporte;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Reporte {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String contenido;
    @OneToOne
    private Usuario emisor;
    private String categoria;
    private EstadoReporte estado;
    private String tipo;
}
