package model;

import java.io.Serializable;
import java.util.Date;

public class bitacora implements Serializable {
    private String fechaHora;
    private String tipoUsuario;
    private String codigoUsuario;
    private String operacion;
    private String estado;
    private String descripcion;

    public bitacora(String tipoUsuario, String codigoUsuario, String operacion, String estado, String descripcion) {
        this.fechaHora = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
        this.tipoUsuario = tipoUsuario;
        this.codigoUsuario = codigoUsuario;
        this.operacion = operacion;
        this.estado = estado;
        this.descripcion = descripcion;
    }

    // Getters
    public String getFechaHora() {
        return fechaHora;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public String getCodigoUsuario() {
        return codigoUsuario;
    }

    public String getOperacion() {
        return operacion;
    }

    public String getEstado() {
        return estado;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
