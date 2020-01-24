package ejercicio.clientes;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase utilitaria que contiene los m�todos necesarios para ejecutar el men�
 * para almacenar los datos de los clientes en el fichero clientes.dat
 *
 * @author Roach
 */
public class Util {
    public static final String LETRAS_DNI = "TRWAGMYFPDXBNJZSQVHLCKE";
// *************************************************************************************************************
// *************************************** M�todos de validaci�n ***********************************************
// *************************************************************************************************************
    
    /**
     * M�todo que comprueba que el tel�fono es v�lido comienza por 6, 7 (Movil)
     * � por 8, 9 (Fijo) y tiene 9 d�gitos en total.
     *
     * @param telefono
     * @return boolean
     */
    public static boolean compruebaTelefono(String telefono) {
        Pattern pattern = Pattern.compile("^[6789][0-9]{8}$");
        return pattern.matcher(telefono).matches();
    }

    /**
     * Metodo que valida un DNI, comprobando que su longitud sea correcta, asi como su Letra corresponde con
     * el numero.
     * @param nif
     * @return boolean
     */
    public static boolean validarNIF(String nif) {
        boolean correcto;
        Pattern pattern = Pattern.compile("(\\d{1,8})([TRWAGMYFPDXBNJZSQVHLCKEtrwagmyfpdxbnjzsqvhlcke])");
        Matcher matcher = pattern.matcher(nif);
        if (matcher.matches()) {
            String letra = matcher.group(2);
            int indiceLetra = Integer.parseInt(matcher.group(1));
            indiceLetra = indiceLetra % 23;
            String buscarReferenciaLetra = LETRAS_DNI.substring(indiceLetra, indiceLetra + 1);
            // Comparamos la letra del DNI llegado por parametro con el orden
            // logico de las letras ignorando que sea mayuscula o minuscula.
            correcto = buscarReferenciaLetra.equalsIgnoreCase(letra);
        } else {
            correcto = false;
        }
        return correcto;
    }
    
    /**
     * Metodo que pinta el menu de opciones que tendra disponible el usuario.
     */
    public static void pintarMenu(){
        System.out.println("*** Men� principal ***");
        System.out.println("1 - A�adir cliente");
        System.out.println("2 - Listar clientes");
        System.out.println("3 - Buscar cliente");
        System.out.println("4 - Borrar cliente");
        System.out.println("5 - Borrar fichero de clientes completamente");
        System.out.println("6 - Salir de la aplicaci�n");
        System.out.println("*** Introduzca la opci�n deseada (1-6) ***");
    }
// *************************************************************************************************************
// *************************************************************************************************************
    
// *************************************************************************************************************  
// ************************************ M�todos del menu de usuario ********************************************
// *************************************************************************************************************    
   public static String introduceCliente(Scanner escanerEntrada){
        boolean condicionDni = false;
        boolean condicionTelefono = false;
        String nombreCliente, nif, direccion;
        int telefono;
        double deuda; 
        
       // Introducir nombre cliente.
       System.out.println("*** Introduce el nombre del cliente ***");
       nombreCliente = escanerEntrada.nextLine();
       
       // Introducir NIF
       System.out.println("*** Introduce el DNI ***");
       do{
           String dniValidar = escanerEntrada.nextLine();
           if(validarNIF(dniValidar)){
               nif = dniValidar;
               condicionDni = true;
           }else{
               System.out.println("*** El DNI introducido no es valido, vuelve a intentarlo. ***");
           }
       }while(!condicionDni);
       
       // Introducir Telefono
       do{
           String telefonoValidar = escanerEntrada.nextLine();
           if(compruebaTelefono(telefonoValidar)){
               telefono = Integer.parseInt(telefonoValidar);
               condicionTelefono = true;
           }else{
               System.out.println("*** El telefono introducido no es valido, vuelve a intentarlo. ***");
           }
       }while(!condicionTelefono);
       
       // Introduce direccion
       System.out.println("*** Introduce la direcci�n del cliente ***");
       direccion = escanerEntrada.nextLine();
       
       // Introduce deuda.
       
       // continuar codigo *********************************************
       
       /// Crear metodo que valide la deuda.....
       /// booleano que compruebe que la deuda no sea un String ni un numero negativo
       //*****
       //*****
       
       
       
       
       /// Crear objeto tipo Cliente y guardarlo en el fichero **************************
       //*******
       //*******
       //*******
       //*******
       
       
       
       
       return "Cliente creado correctamente";
   }
    
    
// *************************************************************************************************************
// *************************************************************************************************************
    
    
// *************************************************************************************************************  
// ****************************************** M�todos de Fichero ***********************************************
// *************************************************************************************************************
    
    
// *************************************************************************************************************
// *************************************************************************************************************
}
