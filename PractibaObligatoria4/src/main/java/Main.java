import Model.FernanByB;
import Model.Reserva;
import Model.Usuario;
import Model.Vivienda;
import Utils.Comms;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static Scanner s = new Scanner(System.in);
    public static FernanByB fernan = new FernanByB();
    public static String claveAdmin = "f3m";
    public static String usuarioLogueado = "";

    public static void main(String[] args) {
        crearUsuario("12345678F","zucaarraul@gmail.com","raul","rz","pass");
        String opt = "";
        while (opt != "salir") {
            pintaMenu();
            opt = s.nextLine();
            if (usuarioLogueado.equals("")) {
                switch (opt) {
                    case "1" -> login();
                    case "2" -> registroUsuario();
                    case "3" -> registroPropietario();
                    case "4" -> registroAdmin();
                    default -> System.out.println("Has introducido un valor incorrecto");
                }
            } else switch (usuarioLogueado.charAt(0)) {
                case '1' :{
                    switch (opt){
                        case "1"-> viviendasEnAlquiler();
                        case "2"-> usuariosSistema();
                        case "3"-> System.out.println("Ver todas las reservas de viviendas");
                        case "4"-> System.out.println("Ver mi perfil");
                        case "5"-> System.out.println("Modificar mi perfil");
                        case "6"-> System.out.println("Cerrar sesion");
                        default -> System.out.println("Has introducido un valor incorrecto");
                    }
                    break;
                }
                case '2' :{
                    switch (opt){
                        case "1"-> System.out.println("Ver mis viviendas en alquiler");
                        case "2"-> System.out.println("Crear/Editar mis viviendas");
                        case "3"-> System.out.println("Ver las reservas de mis viviendas");
                        case "4"-> System.out.println("Establecer un periodo no disponible en una vivienda");
                        case "5"-> System.out.println("Ver mi perfil");
                        case "6"-> System.out.println("Modificar mi perfil");
                        case "7"-> System.out.println("Cerrar sesion");
                        default -> System.out.println("Has introducido un valor incorrecto");
                    }
                    break;
                }
                case '3' :{
                    switch (opt){
                        case "1"-> System.out.println("Busqueda de alojamientos");
                        case "2"-> System.out.println("Ver mis reservas");
                        case "3"-> System.out.println("Modificar mis reservas");
                        case "4"-> System.out.println("Ver mi perfil");
                        case "5"-> System.out.println("Modificar mi perfil");
                        case "6"-> System.out.println("Cerrar sesion");
                        default -> System.out.println("Has introducido un valor incorrecto");
                    }
                    break;
                }
            }

        }
    }
    public static void registroUsuario(){
        if (fernan.usuariosLlenos()) System.out.println("No se puede crear un usuario porque se ha alcanzado el maxmo");
        else {
            System.out.println("Menu de creación de usuario, inserta los campos segun se van pidiendo");
            System.out.print("Nombre: ");
            String nombre=s.nextLine();
            System.out.print("Usuario: ");
            String usuario=s.nextLine();
            System.out.print("Clave: ");
            String pass=s.nextLine();
            System.out.print("DNI: ");
            String dni=s.nextLine();
            System.out.println("E-Mail: ");
            String mail=s.nextLine();
            System.out.println(crearUsuario(dni,mail,nombre,usuario,pass)?"Usuario creado.":"No se ha podido crear el usuario.");
        }
        continuar();
    }
    public static void registroPropietario(){
        if (fernan.propietariosLlenos()) System.out.println("No se puede crear un propietario porque se ha alcanzado el naximo.");
        else {
            System.out.println("Menu de creacion de propietario, inserta los campos segun se van pidiendo");
            System.out.print("Nombre: ");
            String nombre=s.nextLine();
            System.out.print("Usuario: ");
            String usuario=s.nextLine();
            System.out.print("Clave: ");
            String pass=s.nextLine();
            System.out.print("DNI: ");
            String dni=s.nextLine();
            System.out.println("E-Mail: ");
            String mail=s.nextLine();
            System.out.println(crearPropietario(dni,mail,nombre,usuario,pass)?"Propietario creado.":"No se ha podido crear el propietario");
        }
        continuar();
    }
    public static void registroAdmin(){
    if (fernan.adminLleno()) System.out.println("No se puede crear un admin porque se ha llegado al número máximo");
    else{
        System.out.println("Para crear un administrador primero tienes que insertar la clave correspondiente");
        if (!s.nextLine().equals(claveAdmin)) System.out.println("Contraseña incorrecta.");
        else {
            System.out.println("Menu de creación de admin, inserta los campos según se piden");
            System.out.print("Nombre: ");
            String nombre=s.nextLine();
            System.out.print("Usuario: ");
            String usuario=s.nextLine();
            System.out.print("Clave: ");
            String pass=s.nextLine();
            System.out.print("DNI: ");
            String dni=s.nextLine();
            System.out.println("E-Mail: ");
            String mail=s.nextLine();
            System.out.println(crearAdmin(dni,mail,nombre,usuario,pass)?"Admin creado":"No se ha podido crear el admin");
        }
        continuar();
    }
    }
    public static void login(){
        System.out.println("Inserta el usuario con el que quiere loguearte");
        String user=s.nextLine();
        System.out.println("Ahora inserta la contraseña con la que quieres loguearte");
        String pass=s.nextLine();
        usuarioLogueado=fernan.login(user,pass);
        if (usuarioLogueado.equals("")) System.out.println("Credenciales incorrectas.");
        else System.out.println("Te has logueado correctamente");
        continuar();
    }
    public static void viviendasEnAlquiler(){
        pintarViviendas(fernan.viviendasEnAlquiler());
    }
    public static void pintarViviendas(String viviendas){
        if (viviendas.length()==0) System.out.println("No hay viviendas en el sistema");
        else {
            if (viviendas.length()==1) System.out.println(viviendas.equals("1")?fernan.getVivienda1():fernan.getVivienda2());
            else {
                System.out.println(fernan.getVivienda1());
                System.out.println(fernan.getVivienda2());
            }
        }
        continuar();
    }
    public static void usuariosSistema(){
        pintarUsuarios(fernan.usuariosSistema());
    }
    public static void pintarUsuarios(String usuarios){
        if (usuarios.length()==0) System.out.println("No hay ningun usuario en el sistema");
        else {
            if (usuarios.length()==1) System.out.println(usuarios.equals("1")?fernan.getUsuario1():fernan.getUsuario2());
            else{
                System.out.println(fernan.getUsuario1());
                System.out.println(fernan.getUsuario2());
            }
        }
        continuar();
    }
    public static void pintaMenu() {
        if (usuarioLogueado.equals("")) System.out.println("""
                ==============================================
                │           Bienvenido a FernanB&B           
                ==============================================
                │  Que quieres hacer                         
                │  1.-Loguearme                              
                │  2.-Registrarme como usuario               
                │  3.-Registrarme como propietario           
                │  4.-Registrarme como administrador         
                ==============================================
                """);
        else {
            switch (usuarioLogueado.charAt(0)) {
                case '1' -> System.out.printf("""
                        =============================================
                                                                   
                         Menu de admin                             
                         Bienvenido: %s                            
                        =============================================   
                         1.-Ver todas las viviendas en alquiler    
                         2.-Ver todos los usuarios del sistema        
                         3.-Ver todas las reservas de viviendas       
                         4.-Ver mi perfil                          
                         5.-Modificar mi perfil                       
                         6.-Cerrar sesión                          
                         ============================================                                           
                        """,fernan.getAdminById(usuarioLogueado).getNombre());
                case '2' -> System.out.printf("""
                        ================================================================
                                                                                      
                          Menu de propietario                                         
                          Bienvenido: %s                                              
                        ================================================================
                          1.-Ver mis viviendas en alquiler                            
                          2.-Crear/Editar mis viviendas                               
                          3.-Ver las reservas de mis viviendas                        
                          4.-Establecer un periodo de no disponible para una vivienda 
                          5.-Ver mi perfil                                            
                          6.-Modificar mi perfil                                      
                          7.-Cerrar sesión                                            
                                                                                      
                        ================================================================
                        """,fernan.getPropietarioById(usuarioLogueado).getNombre());
                case '3' -> System.out.printf("""
                        ==========================================
                                                                 
                          Menu de usuario                       
                          Bienvenido: %s                        
                        ==========================================
                          1.-Busqueda de alojamientos           
                          2.-Ver mis reservas                   
                          3.-Modificar mis reservas             
                          4.-Ver mi perfil                      
                          5.-Modificar mi perfil                
                          6.-Cerrar sesión                      
                                                                  
                        ==========================================
                        """,fernan.getUsuarioById(usuarioLogueado).getnombre());
            }
        }
    }

    public static boolean crearPropietario(String dni, String mail, String nombre, String user, String pass) {
        boolean created = false;
        if (validaEmail(mail) && validaDni(dni)) {
            String token = generarToken();
            if (fernan.creaPropietario(dni.toUpperCase(), mail, user, pass, nombre, token)) created = true;
            enviarToken(token, mail);
            tokenEnviado();
        }
        return created;
    }

    public static boolean crearUsuario(String dni, String mail, String nombre, String user, String pass) {
        boolean created = false;
        if (validaEmail(mail) && validaDni(dni)) {
            String token = generarToken();
            if (fernan.creaUsuario(dni.toUpperCase(), mail, user, pass, nombre, token)) created = true;
            enviarToken(token, mail);
            tokenEnviado();
        }
        return created;
    }

    public static boolean crearAdmin(String dni, String mail, String nombre, String user, String pass) {
        boolean created = false;
        if (validaEmail(mail) && validaDni(dni)) {
            String token = generarToken();
            if (fernan.crearAdmin(dni.toUpperCase(), mail, user, pass, nombre, token)) created = true;
            enviarToken(token, mail);
            tokenEnviado();
        }
        return created;
    }

    public static boolean validaEmail(String mail) {
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = pattern.matcher(mail);

        return matcher.find();
    }

    public static boolean validaDni(String dni) {
        boolean correct = true;
        if (dni.length() != 9) return false;
        else {
            for (int i = 0; i < 8; i++) {
                if (Character.isLetter(dni.charAt(i))) correct = false;
            }
            if (Character.isDigit(dni.charAt(8))) correct = false;
        }
        return correct;
    }

    public static String generarToken() {
        return String.valueOf((int) (Math.random() * 10000));
    }

    public static void enviarToken(String token, String mail) {
        Comms.enviarMensajeTelegram("El token para verificar tu usuario es: " + token);
        Comms.enviarConGMail(mail, "Tu token de fernanbyb", token);
    }

    public static void verificarUsuario(String id) {
        System.out.print("Hola, " + fernan.getNombreById(id) + " estas intentando verificar tu cuenta, inserta a continuación el codigo que hemos enviado a tu correo o telegram.\n" +
                "token: ");
        System.out.println(fernan.verificarUsuario(id, s.nextLine()) ? "Usuario verificado correctamente." : "No se ha podido verificar tu usuario.");
        continuar();
    }

    public static void tokenEnviado() {
        System.out.println("Se ha enviado un token a tu correo electronico y a telegram, verificalo para poder acceder a todas las funcionalidades de FernanB&B");
    }

    public static void continuar() {
        System.out.print("Pulsa ENTER para continuar.");
        s.nextLine();
    }
}