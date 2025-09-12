/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ramirito
 */
public class gestorBitacora {
   
    bitacora[] registros = new bitacora[200]; // máximo 200 acciones
    int contador = 0;

    // Agregar un registro
    public void registrarAccion(String accion, boolean correcta, String usuario) {
        registros[contador] = new bitacora(accion, correcta, usuario);
        contador++;
    }

    // Mostrar todas las acciones
    public void mostrarBitacora() {
        if (contador == 0) {
            System.out.println("No hay acciones registradas.");
        } else {
            for (int i = 0; i < contador; i++) {
                registros[i].mostrarInfo();
            }
        }
    }
}

    

