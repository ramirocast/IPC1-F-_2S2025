package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class sesion implements Serializable {
    private String codigoUsuario;
    private String fechaInicio;
    private boolean activa;

    public sesion(String codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
        this.fechaInicio = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
        this.activa = true;
    }

    public String getCodigoUsuario() { return codigoUsuario; }
    public String getFechaInicio() { return fechaInicio; }
    public boolean isActiva() { return activa; }

    public void cerrar() { activa = false; }

    @Override
    public String toString() {
        return "Sesion de " + codigoUsuario + " iniciada el " + fechaInicio + (activa ? " (activa)" : " (cerrada)");
    }
}
