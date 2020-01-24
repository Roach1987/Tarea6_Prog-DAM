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
        boolean condicionMenu = false;
        do{
            Util.pintarMenu();
            String opcion = escanerEntrada.nextLine();
            switch(opcion){
                case "1":
                    
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
                    break;
            }
        }while(!condicionMenu);
    }
}
