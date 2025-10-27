package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class bitacora implements Serializable {
    private String usuario;
    private String operacion;
    private String descripcion;
    private String fecha;

    public bitacora(String usuario, String operacion, String descripcion) {
        this.usuario = usuario;
        this.operacion = operacion;
        this.descripcion = descripcion;
        this.fecha = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
    }

    public String getUsuario() { return usuario; }
    public String getOperacion() { return operacion; }
    public String getDescripcion() { return descripcion; }
    public String getFecha() { return fecha; }

    @Override
    public String toString() {
        return "[" + fecha + "] " + usuario + " - " + operacion + ": " + descripcion;
    }
}
