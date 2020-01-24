/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicio.clientes;

import java.util.Scanner;

/**
 *
 * @author Roach
 */
public class Main {
    public static void main(String [] args){
        Scanner escanerEntrada = new Scanner(System.in);
        String mensaje;
        boolean condicionMenu = false;
        Util.pintarMenu();
        do{
            String opcion = escanerEntrada.nextLine();
            switch(opcion){
                case "1":
                    mensaje = Util.introduceCliente(escanerEntrada);
                    System.out.println(mensaje);
                    break;
                case "2":
                    
                    break;
                case "3":
                    
                    break;    
                case "4":
                    
                    break;
                case "5":
                    
                    break;
                case "6":
                    
                    break;
                default:
                    System.out.println("*** opcion Incorrecta, vuelva a intentarlo ***");
                    System.out.println("*** opciones validas del 1 al 6 ***");
                    break;
            }
        }while(!condicionMenu);
    }
}
