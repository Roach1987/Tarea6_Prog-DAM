package ejercicio.clientes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase utilitaria que contiene los métodos necesarios para ejecutar el menú
 * para almacenar los datos de los clientes en el fichero clientes.dat
 *
 * @author Roach
 */
public class Util implements Serializable {

    public static final long serialVersionUID = 1L;
    public static final String LETRAS_DNI = "TRWAGMYFPDXBNJZSQVHLCKE";
    public static final String MENSAJE_OK = "OK";
    public static final String ARCHIVO_CLIENTES = "clientes.dat";
    public static final String BUSCAR_CLIENTE = "buscar";
    public static final String BORRAR_CLIENTE = "borrar";
    private static ArrayList<Cliente> listaObjetosCliente = new ArrayList<Cliente>();
// *************************************************************************************************************
// *************************************** Métodos de validación ***********************************************
// *************************************************************************************************************

    /**
     * Método que comprueba que el teléfono es válido comienza por 6, 7 (Movil)
     * ó por 8, 9 (Fijo) y tiene 9 dígitos en total.
     *
     * @param telefono
     * @return boolean
     */
    public static boolean compruebaTelefono(String telefono) {
        Pattern pattern = Pattern.compile("^[6789][0-9]{8}$");
        return pattern.matcher(telefono).matches();
    }

    /**
     * Metodo que valida un DNI, comprobando que su longitud sea correcta, asi
     * como su Letra corresponde con el numero.
     *
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
     * Método que valida que la deuda no sea un numero negativo, ni una cadena
     * de caracteres
     *
     * @param deuda
     * @return
     */
    @SuppressWarnings("UnusedAssignment")
    public static String compruebaDeuda(String deuda) {
        String resultado;
        int deudaNumerica = 0;
        try {
            if (null != deuda && !deuda.isEmpty()) {
                deudaNumerica = Integer.parseInt(deuda);
            } else {
                resultado = "El campo deuda no esta informado.";
            }
            resultado = (deudaNumerica > 0) ? MENSAJE_OK : "La deuda no puede ser un numero negativo.";
        } catch (NumberFormatException ex) {
            resultado = "La deuda no es numerica.";
        }
        return resultado;
    }

    /**
     * Metodo que pinta el menu de opciones que tendra disponible el usuario.
     */
    public static void pintarMenu() {
        System.out.println("*** Menú principal ***");
        System.out.println("1 - Añadir cliente");
        System.out.println("2 - Listar clientes");
        System.out.println("3 - Buscar cliente");
        System.out.println("4 - Borrar cliente");
        System.out.println("5 - Borrar fichero de clientes completamente");
        System.out.println("6 - Salir de la aplicación");
        System.out.println("*** Introduzca la opción deseada (1-6) ***");
    }
    
   /**
     * Método utilitario para crear separación en el menu.
     */
    public static void limpiarPantalla() {
        int x = 0;
        while (x < 3) {
            System.out.println();
            x++;
        }
    }

// *************************************************************************************************************
// *************************************************************************************************************

// *************************************************************************************************************  
// ************************************ Métodos del menu de usuario ********************************************
// *************************************************************************************************************    
    public static void introduceCliente(Scanner escanerEntrada) {
        boolean condicionDni = false;
        boolean condicionTelefono = false;
        boolean condicionDeuda = false;
        String nombreCliente = "";
        String nif = "";
        String direccion = "";
        int telefono = 0;
        double deuda = 0.0;

        // Introducir nombre cliente.
        System.out.println("*** Introduce el nombre del cliente ***");
        nombreCliente = escanerEntrada.nextLine();

        // Introducir NIF
        System.out.println("*** Introduce el DNI ***");
        do {
            String dniValidar = escanerEntrada.nextLine();
            if (validarNIF(dniValidar)) {
                nif = dniValidar;
                condicionDni = true;
            } else {
                System.out.println("*** El DNI introducido no es valido, vuelve a intentarlo. ***");
            }
        } while (!condicionDni);

        // Introducir Telefono
        System.out.println("*** Introduce el telefono ***");
        do {
            String telefonoValidar = escanerEntrada.nextLine();
            if (compruebaTelefono(telefonoValidar)) {
                telefono = Integer.parseInt(telefonoValidar);
                condicionTelefono = true;
            } else {
                System.out.println("*** El telefono introducido no es valido, vuelve a intentarlo. ***");
            }
        } while (!condicionTelefono);

        // Introduce direccion
        System.out.println("*** Introduce la dirección del cliente. ***");
        direccion = escanerEntrada.nextLine();

        // Introduce deuda.
        System.out.println("*** Introduce la deuda del cliente. ***");
        do {
            String deudaEntrada = escanerEntrada.nextLine();
            String deudaValidada = compruebaDeuda(deudaEntrada);
            if (deudaValidada.equals(MENSAJE_OK)) {
                deuda = Integer.parseInt(deudaEntrada);
                condicionDeuda = true;
            } else {
                System.out.println("*** Error al introducir la deuda, mensaje: " + deudaValidada + " ***");
                System.out.println("*** Vuelve a introducir la deuda. ***");
            }
        } while (!condicionDeuda);

        // Instanciamos el cliente, con los datos recogidos.
        Cliente clienteCreado = new Cliente(nif, nombreCliente, telefono, direccion, deuda);

        // Invocamos el metodo que realizara la inserción del nuevo cliente.
        ficheroAnadirCliente(clienteCreado);
    }
    
    /**
     * Método utilitario que pide dni de cliente a buscar.Se realiza para liberar de logica el Main.
     * @param escanerEntrada
     * @param condicion 
     */
    public static void clienteBuscarBorrar(Scanner escanerEntrada, String condicion){
        ArrayList<Cliente> listaClientes = cargarListaClientes();
        // Validamos si el fichero no esta vacio, de estarlo le devolveremos 
        // un mensaje indicativo al usuario.
        if(null != listaClientes && !listaClientes.isEmpty()){
            boolean condicionDni = false;
            String dniValido = "";
            do{
                System.out.println("*** Introduce DNI del cliente a buscar. ***");
                String dniEntrada = escanerEntrada.nextLine();
                if(Util.validarNIF(dniEntrada)){
                    dniValido = dniEntrada;
                    condicionDni = true;
                }else{
                    System.out.println("*** El DNI introducido no es valido. ***");
                    limpiarPantalla();
                }
            }while(!condicionDni);
            
            if(condicion.equals(BUSCAR_CLIENTE) ){
                BuscarCliente(dniValido, listaClientes);
            }else{
                borrarCliente(dniValido, listaClientes);
            }
        }else{
            System.out.println("*** El fichero esta vacio, no se puede realizar la busqueda. ***");
        }
    }
    
    /**
     * Método que busca un cliente con un dni llegado por parametro en el fichero.
     * @param dni 
     * @param clientes 
     */
    public static void BuscarCliente(String dni, ArrayList<Cliente> clientes){
        ArrayList<Cliente> clientesEncontrados = new ArrayList<>();
        
        // Buscamos el/los datos que coinciden con el dni.
        for(Cliente cliente : clientes){
            if(cliente.getNif().equalsIgnoreCase(dni)){
                clientesEncontrados.add(cliente);
            }
        }
        
        // Recorremos los clientes encontrados para pintarlos.
        if(!clientesEncontrados.isEmpty()){
            String mensaje = (clientesEncontrados.size() > 1) ? "*** Datos relacionados con DNI " + dni + " ***"
                    : "*** Datos del cliente con DNI " + dni + " ***";
            System.out.println(mensaje);
            int contador = 1;
            for(Cliente cliente : clientesEncontrados){
                System.out.println("**************** Dato nº" + contador + " ********************");
                System.out.println(cliente.toString());
                System.out.println("****************************************************************");
                contador++;
            }
        }else{
            System.out.println("*** No se ha encontrado ningun cliente con DNI " + dni);
        }
    }
    
    /**
     * Método que borra un cliente con un dni llegado por parametro en el fichero.
     * @param dni
     * @param clientes 
     */
    public static void borrarCliente(String dni, ArrayList<Cliente> clientes){
        
        boolean clienteEncontrado = false;
        ArrayList<Cliente> clientesBorrar = new ArrayList<>();
        // Recorremos la lista de clientes, y borramos todos los datos 
        // de los clientes que coincidan con el DNI.
        for(Cliente cliente : clientes){
            if(cliente.getNif().equalsIgnoreCase(dni)){
                 clientesBorrar.add(cliente);
                 clienteEncontrado = true;
            }
        }
                
        for(Cliente cliente : clientesBorrar){
            clientes.remove(cliente);
        }
        
        if(clienteEncontrado){ 
            try{
                ObjectOutputStream objetoSalida;
                try (FileOutputStream ficheroSalida = new FileOutputStream(ARCHIVO_CLIENTES);) {
                    objetoSalida = new ObjectOutputStream(ficheroSalida);
                    objetoSalida.writeObject(clientes);
                }
                objetoSalida.close();
            }catch(IOException ex){
                System.out.println("*** Error en fichero " + ex.getMessage() + " ***");
            }
        }else{
            System.out.println("*** No existen un cliente con el DNI " + dni + " ***");
        }
        System.out.println("*** Se han eliminado los datos del cliente con DNI " + dni + " ***");
    }

// *************************************************************************************************************
// *************************************************************************************************************
// *************************************************************************************************************  
// ****************************************** Métodos de Fichero ***********************************************
// *************************************************************************************************************
   /**
     * Método queComprueba si existe el fichero, si no lo crea.
     */
    public static void ficheroCrear() {
        File fichero = new File(ARCHIVO_CLIENTES);
        try {
            if (!fichero.exists()) {
                fichero.createNewFile();
            }
        } catch (IOException ex) {
            System.out.println("Error al crear el fichero.");
        }
    }
    
    /**
     * Método que añade un nuevo cliente al fichero.
     *
     * @param cliente
     */
    public static void ficheroAnadirCliente(Cliente cliente) {
        try {
            // Intentamos crear el fichero y su objeto dentro de un try pasando por parametro 
            // estos dos Streams sin catch, si falla por estar vacio no lanzara ninguna excepcion y añadira
            // el cliente con el objeto ObjectOutputStream
            // "try con autocierre de Streams"
            try (
                // lee la informacion del archivo.
                FileInputStream ficheroEntrada = new FileInputStream(ARCHIVO_CLIENTES);
                    
                // traduce la infromacion del archivo en datos
                ObjectInputStream objetoEntrada = new ObjectInputStream(ficheroEntrada) 
            ) {
                // lee todos los objetos que esten en el array
                listaObjetosCliente = (ArrayList<Cliente>) objetoEntrada.readObject();
            }
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("El fichero esta vacío");
        }

        // Introduce en el array los datos del nuevo cliente
        listaObjetosCliente.add(cliente);

        ObjectOutputStream objetoSalida;
        // Se crea el flujo para poder escribir en "clientes.dat"
        try (FileOutputStream ficheroSalida = new FileOutputStream(ARCHIVO_CLIENTES)) {
            
            // prepara la forma de escritura para "clientes.dat" que en este caso sera escribir un objeto
            objetoSalida = new ObjectOutputStream(ficheroSalida);
            
            // Escribe en el archivo el Array de objetos "objetoArrayCliente"
            objetoSalida.writeObject(listaObjetosCliente);
            
            // Cerramos el objeto de salida.
            objetoSalida.close();
        } catch (IOException ex) {
            System.out.println("Error en fichero " + ex.getMessage());
        }
        System.out.println("El cliente con NIF " + cliente.getNif() + " ha sido añadido correctamente al fichero");
    }

    /**
     * Método que borra un fichero si este existe.
     *
     */
    public static void ficheroBorrar() {
        File fichero = new File(ARCHIVO_CLIENTES);
        if (fichero.exists()) {
            fichero.delete();
        }else{
            System.out.println("*** El fichero no existe, no se puede borrar. ***");
        }
    }

    /**
     * Método que imprime por pantalla el contenido del fichero, si este esta
     * vacio imprime un mensaje indicativo.
     *
     */
    public static void mostrarClientes() {
        try {
            // Si el fichero no esta vacio pintamos los clientes que tiene.
            if (!cargarListaClientes().isEmpty()) {
                ArrayList<Cliente> listaClientes = cargarListaClientes();
                System.out.println("*** La lista de clientes es la siguiente: ***");
                int contador = 1;
                for (Cliente cliente : listaClientes) {
                    System.out.println("**************** Cliente nº" + contador + " ********************");
                    System.out.println(cliente.toString());
                    System.out.println("***************************************************");
                    contador++;
                }
            } else {
                System.out.println("El fichero no tiene ningun registro en este momento");
            }
        } catch (Exception e) {
            System.out.println("Error al cargar el fichero: " + e.getMessage());
        }
    }

    /**
     * Metodo que lee el fichero de flujo de entrada y devuelve una lista de
     * cliente de este flujo.
     *
     * @param fichero
     * @return
     */
    private static ArrayList<Cliente> cargarListaClientes() {
        try {
            // lee la informacion del archivo
            FileInputStream ficheroEntrada = new FileInputStream(ARCHIVO_CLIENTES);
            
            // traduce la informacion del archivo en datos
            ObjectInputStream objetoEntrada = new ObjectInputStream(ficheroEntrada);    
            
            // lee todos los objetos que esten en el array
            listaObjetosCliente = (ArrayList<Cliente>) objetoEntrada.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("El fichero esta vacío");
        }
        return listaObjetosCliente;
    }
// *************************************************************************************************************
// *************************************************************************************************************
}
