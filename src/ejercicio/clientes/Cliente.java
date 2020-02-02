package ejercicio.clientes;

import java.io.Serializable;

/**
 *
 * @author Roach
 */
public class Cliente implements Serializable{
    
    // Declaramos el atributo para que la clase no pierda la serialización.
    public static final long serialVersionUID = 1L;
    
    // Atributos de clase.
    private String nif;
    private String nombre;
    private int telefono;
    private String direccion;
    private double deuda;
    
    // Constructores.
    public Cliente(){
    }
    
    public Cliente(String nif, String nombre, int telefono, String direccion, double deuda){
        this.nif = nif;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.deuda = deuda;
    }
    
    // Getters y Setters.
    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public double getDeuda() {
        return deuda;
    }

    public void setDeuda(double deuda) {
        this.deuda = deuda;
    }
    
    // toString()
    @Override
    public String toString(){
        return  "Nombre: " + this.nombre + "\n" +
                "Nif: " + this.nif + "\n" + 
                "Telefono: " + this.telefono + "\n" + 
                "Direccion: " + this.direccion + "\n" +
                "Deuda: " + this.deuda;
    }
}