package com.wsmasico.model;

import lombok.Data;
import lombok.ToString;

@Data
public class Mensaje {
    private String numero;
    private String nombreCompleto;
    private String puesto;
    private String fecha;
    private String lugar;
    @ToString.Exclude
    private String mensaje;


}
