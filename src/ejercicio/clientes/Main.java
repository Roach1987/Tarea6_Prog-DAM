package ejercicio.clientes;

import java.io.Serializable;
import java.util.Scanner;

/**
 *
 * @author Roach
 */
public class Main implements Serializable{
    public static final long serialVersionUID = 1L;
    
    public static void main(String [] args){
        Scanner escanerEntrada = new Scanner(System.in);
        // Creamos el fichero con el que vamos a trabajar.
        Util.ficheroCrear();
        boolean condicionMenu = false;
        int contador = 0;
        do{
            if(contador > 0) Util.limpiarPantalla();
            Util.pintarMenu();
            String opcion = escanerEntrada.nextLine();
            switch(opcion){
                case "1":
                    Util.introduceCliente(escanerEntrada);
                    break;
                case "2":
                    Util.mostrarClientes();
                    break;
                case "3":
                    Util.clienteBuscarBorrar(escanerEntrada, Util.BUSCAR_CLIENTE);
                    break;    
                case "4":
                    Util.clienteBuscarBorrar(escanerEntrada, Util.BORRAR_CLIENTE);
                    break;
                case "5":
                    Util.ficheroBorrar();
                    break;
                case "6":
                    System.out.println("*** Adios, hasta pronto!!! ***");
                    condicionMenu = true;
                    break;
                default:
                    System.out.println("*** opcion Incorrecta, vuelva a intentarlo ***");
                    System.out.println("*** opciones validas del 1 al 6 ***");
                    break;
            }
            contador++;
        }while(!condicionMenu);
    }
}
