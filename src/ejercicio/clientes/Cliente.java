package ejercicio.clientes;

import java.io.Serializable;

/**
 *
 * @author Roach
 */
public class Cliente implements Serializable{
    
    // Declaramos el atributo para que la clase no pierda la serializaci�n.
    public static final long serialVersionUID = 1L;
    private String nif;
    private String nombre;
    private int telefono;
    private String direccion;
    private double deuda;
}
