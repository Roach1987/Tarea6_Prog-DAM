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
public class Util implements Serializable{
    
    public static final long serialVersionUID = 1L;
    public static final String LETRAS_DNI = "TRWAGMYFPDXBNJZSQVHLCKE";
    public static final String MENSAJE_OK = "OK";
    public static final String RUTA_FICHERO = "C:\\Users\\Roach_Mimi\\clientes.dat";
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
// *************************************************************************************************************
// *************************************************************************************************************

// *************************************************************************************************************  
// ************************************ Métodos del menu de usuario ********************************************
// *************************************************************************************************************    
    public static String introduceCliente(Scanner escanerEntrada, File fichero) {
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
            }
        } while (!condicionDeuda);

        // Instanciamos el cliente, con los datos recogidos.
        Cliente clienteCreado = new Cliente(nif, nombreCliente, telefono, direccion, deuda);

        // Comprobamos si el fichero esta abierto si no se abre, si no existe se crea.
        // Añadimos la instancia del Cliente al fichero.
        ficheroAnadirCliente(fichero, clienteCreado);

        return "Cliente creado correctamente";
    }

// *************************************************************************************************************
// *************************************************************************************************************
// *************************************************************************************************************  
// ****************************************** Métodos de Fichero ***********************************************
// *************************************************************************************************************
    /**
     * Método que comprueba que el fichero existe si no lo crea, y a
     * continuación añade el cliente al fichero
     *
     * @param fichero
     * @param cliente
     */
    public static void ficheroAnadirCliente(File fichero, Cliente cliente) {
        if (null != fichero && fichero.exists()) {
            ficheroAnadir(fichero, cliente);
        } else {
            fichero = ficheroCrear();
            ficheroAnadir(fichero, cliente);
        }
    }

    /**
     * Método que añade un nuevo cliente al fichero.
     * @param fichero
     * @param cliente 
     */
    public static void ficheroAnadir(File fichero, Cliente cliente) {
        
        // Declaramos los Streams que vamos a utilizar en el proceso, tambien la lista que va
        // a albergar los objetos tipo Cliente.
        FileInputStream ficheroEntrada = null;
        FileOutputStream ficheroSalida = null;
        ObjectInputStream objetoEntrada = null;
        ObjectOutputStream objetoSalida = null;
        ArrayList<Cliente> listaObjetosCliente = null;
        
        try {          
            listaObjetosCliente = new ArrayList<>();
            
            if (!fichero.exists()){
                fichero = ficheroCrear();
            }
            // Leemos la información del fichero.
            ficheroEntrada = new FileInputStream(fichero);
            
            // Traducimos la información del archivo en datos.
            objetoEntrada = new ObjectInputStream(ficheroEntrada); 

            // Leemos todos los objetos que estan en el array
            listaObjetosCliente = (ArrayList<Cliente>) objetoEntrada.readObject(); 

            // Introducimos en la lista los datos del nuevo cliente
            listaObjetosCliente.add(cliente);
            
            // Se crea el flujo para poder escribir en "clientes.dat"
            ficheroSalida = new FileOutputStream(fichero); 

            // Prepara la forma de escritura para "clientes.dat" que en este caso sera escribir un objeto
            objetoSalida = new ObjectOutputStream(ficheroSalida); 
            
            // Escribimos la lista en el fichero
            objetoSalida.writeObject(listaObjetosCliente);

            System.out.println("El cliente con NIF " + cliente.getNif() + " ha sido añadido correctamente al fichero");
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Error en el fichero: " + ex.getMessage());
        } finally {
            try {
                // Cerramos los Streams tanto de entrada como de salida.
                if(null != ficheroEntrada){
                    ficheroEntrada.close();            
                }
                if(null != objetoEntrada){
                    objetoEntrada.close();
                }
                if(null != ficheroSalida){
                    ficheroSalida.close();
                }
                if(null != objetoSalida){
                    objetoSalida.close();
                }
            } catch (IOException ex) {
                System.out.println("Error en el fichero: " + ex.getMessage());
            }
        }
    }


    /**
     * Método que crea el fichero "clientes.dat"
     *
     * @return File
     */
    public static File ficheroCrear() {
        File fichero = null;
        try {
            fichero = new File(RUTA_FICHERO);
            fichero.createNewFile();
        } catch (IOException ex) {
            System.out.println("Error al crear el fichero.");
        }
        return fichero;
    }

    /**
     * Método que borra un fichero si este existe.
     *
     * @param fichero
     */
    public static void ficheroBorrar(File fichero) {
        if (fichero.exists()) {
            fichero.delete();
        }
    }

    /**
     * Método que imprime por pantalla el contenido del fichero, si este esta vacio imprime
     * un mensaje indicativo.
     * @param fichero 
     */
    public static void mostrarClientes(File fichero) {
        try {
            if (!fichero.exists()) {
                fichero = ficheroCrear();
            }
            // con "isEmpty()"sabremos si el archivo tiene infromacion escrita o no
            if (!cargarListaClientes(fichero).isEmpty()) {
                ArrayList<Cliente> listaClientes = cargarListaClientes(fichero);
                System.out.println("*** La lista de clientes es la siguiente: ***");
                for (Cliente cliente : listaClientes) {
                    System.out.println("***************************************************");
                    cliente.toString();
                    System.out.println("***************************************************");
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
    private static ArrayList<Cliente> cargarListaClientes(File fichero) {
        ArrayList<Cliente> listaCliente = null;
        try {
            FileInputStream ficheroFlujoEntrada = new FileInputStream(fichero);// lee la informacion del archivo
            ObjectInputStream objetoFlujoEntrada = new ObjectInputStream(ficheroFlujoEntrada); // traduce la informacion del archivo en datos       
            listaCliente = (ArrayList<Cliente>) objetoFlujoEntrada.readObject(); // lee todos los objetos que esten en el array
            // traduce la infromacion del archivo en datos
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("El fichero esta vacío");
        }
        return listaCliente;
    }
// *************************************************************************************************************
// *************************************************************************************************************
}
