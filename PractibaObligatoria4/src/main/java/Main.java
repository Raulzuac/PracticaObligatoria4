import Model.FernanByB;
import Model.Reserva;
import Model.Usuario;
import Model.Vivienda;
import Utils.Comms;

import javax.crypto.spec.PSource;
import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static Scanner s = new Scanner(System.in);

    public static void main(String[] args) {
        FernanByB fernan = new FernanByB();
        String usuarioLogueado = "";
        crearUsuario("12345678F", "zucaarraul@gmadil.com", "raul", "rz", "pass", fernan);
        String opt = "";
        while (opt != "salir") {
            pintaMenu(fernan, usuarioLogueado);
            opt = s.nextLine();
            if (usuarioLogueado.equals("")) {
                switch (opt) {
                    case "1" -> usuarioLogueado = login(fernan, usuarioLogueado);
                    case "2" -> registroUsuario(fernan);
                    case "3" -> registroPropietario(fernan);
                    case "4" -> registroAdmin(fernan);
                    default -> System.out.println("Has introducido un valor incorrecto");
                }
            } else switch (usuarioLogueado.charAt(0)) {
                case '1': {
                    switch (opt) {
                        case "0" -> {
                            if (fernan.getAdminById(usuarioLogueado).verificado())
                                System.out.println("Has introducido un valor incorrecto");
                            else verificarUsuario(fernan, usuarioLogueado);
                        }
                        case "1" -> viviendasEnAlquiler(fernan);
                        case "2" -> usuariosSistema(fernan);
                        case "3" -> pintaPersona(fernan, usuarioLogueado);
                        case "4" -> reservasSistema(fernan);
                        case "5" -> modificarUsuario(fernan, usuarioLogueado);
                        case "6" -> usuarioLogueado = "";
                        default -> System.out.println("Has introducido un valor incorrecto");
                    }
                    break;
                }
                case '2': {
                    switch (opt) {
                        case "0" -> {
                            if (fernan.getPropietarioById(usuarioLogueado).verificado())
                                System.out.println("Has introducido un valor incorrecto");
                            else verificarUsuario(fernan, usuarioLogueado);
                        }
                        case "1" -> verViviendasPropietario(fernan, usuarioLogueado);
                        case "2" -> creaEditaVivienda(fernan, usuarioLogueado);
                        case "3" -> verReservasMiVivienda(fernan, usuarioLogueado);
                        case "4" -> bloqueaFechaVivienda(fernan, usuarioLogueado);
                        case "5" -> pintaPersona(fernan, usuarioLogueado);
                        case "6" -> modificarUsuario(fernan, usuarioLogueado);
                        case "7" -> usuarioLogueado = "";
                        default -> System.out.println("Has introducido un valor incorrecto");
                    }
                    break;
                }
                case '3': {
                    switch (opt) {
                        case "0" -> {
                            if (fernan.getUsuarioById(usuarioLogueado).verificado())
                                System.out.println("Has introducido un valor incorrecto");
                            else verificarUsuario(fernan, usuarioLogueado);
                        }
                        case "1" -> buscaVivienda(fernan, usuarioLogueado);
                        case "2" -> verReservasUsuario(fernan, usuarioLogueado);
                        case "3" -> usuarioModificaReservas(fernan, usuarioLogueado);
                        case "4" -> pintaPersona(fernan, usuarioLogueado);
                        case "5" -> modificarUsuario(fernan, usuarioLogueado);
                        case "6" -> usuarioLogueado = "";
                        default -> System.out.println("Has introducido un valor incorrecto");
                    }
                    break;
                }
            }

        }
    }

    private static void buscaVivienda(FernanByB fernan, String usuarioLogueado) {
        if(fernan.getUsuarioById(usuarioLogueado).reservasLlenas()){
            System.out.println("Lo sentimos, no puedes reservar porque tienes las reservas llenas");
        }else {
            LocalDate fini = null, ffin = null;
            String localidad, viviendasEncontradas;
            Vivienda v=null;
            System.out.println("Vienvenido al menú de busqueda de vivienda de FernanB&B");
            System.out.println("Introduce la fecha en la que iniciarías tu estancia (dd-mm-aaaa)");
            do {
                try {
                    fini = LocalDate.parse(s.nextLine(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                } catch (DateTimeException e) {
                    System.out.println("Introduce la fecha en un formato correcto dd-mm-aaaa");
                }
            } while (fini == null);
            System.out.println("Ahora introducec la fecha en la que acabará la estancia");
            do {
                try {
                    ffin = LocalDate.parse(s.nextLine(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                } catch (DateTimeException e) {
                    System.out.println("Introduce la fecha en un formato correcto dd-mm-aaaa");
                }
            } while (ffin == null);
            System.out.println("Inserta la localidad en la que quieres buscar alojamiento");
            localidad = s.nextLine();
            viviendasEncontradas = fernan.buscaVivienda(fini, ffin, localidad);
            if (viviendasEncontradas.equals("")) {
                System.out.println("No se ha encontrado ninguna vivienda que cumpla sus requisitos");
                continuar();
            } else {
                if (viviendasEncontradas.length() == 1) {
                    System.out.println("Las viviendas encontradas son: \n1:\n" + ((viviendasEncontradas.equals("1")) ? fernan.getVivienda1() : fernan.getVivienda2()) + "Itroduce el número de la vivienda correspondiente, cualquier otro para cancelar la reserva");
                    if (!s.nextLine().equals("1")) {
                        System.out.println("Cancelando la resrva.");
                        continuar();
                    } else v = (viviendasEncontradas.equals("1")) ? fernan.getVivienda1() : fernan.getVivienda2();
                } else {
                    System.out.println("Las viviendas encontradas son: \n1:\n"+ fernan.getVivienda1()+"\n2:\n"+fernan.getVivienda2());
                    switch (s.nextLine()){
                        case "1"->v=fernan.getVivienda1();
                        case "2"->v=fernan.getVivienda2();
                        default ->  {
                            System.out.println("Cancelando la reserva");
                            continuar();
                        }
                    }
                }

            }
            if (v!=null){
                System.out.println("Su vivienda ha sido reservada correctamente");
                fernan.reservaVivienda(fini,ffin,v,usuarioLogueado);
                continuar();
            }
        }
    }

    private static void usuarioModificaReservas(FernanByB fernan, String usuarioLogueado) {
        Reserva r;
        if (!fernan.getUsuarioById(usuarioLogueado).tieneReservas()) {
            System.out.println("No tienes ninguna reserva que modificar");
            continuar();
        } else {
            System.out.println("Estas son tus reservas: ");
            if (fernan.getUsuarioById(usuarioLogueado).getReserva1() != null)
                System.out.println(fernan.getUsuarioById(usuarioLogueado).getReserva1());
            if (fernan.getUsuarioById(usuarioLogueado).getReserva2() != null)
                System.out.println(fernan.getUsuarioById(usuarioLogueado).getReserva2());
            System.out.println("Introduce el id de la reserva que quieres modificar.");
            if ((r = fernan.getUsuarioById(usuarioLogueado).getReservaById(s.nextLine())) == null) {
                System.out.println("Id incorrecto volviendo al menú de usuario");
            } else {
                //todo
                System.out.println("¿Quieres borrar la reserva?");
                if (!Character.toString(s.nextLine().charAt(0)).equalsIgnoreCase("s")) {
                    System.out.println("Cancelando la modificación de reserva");
                    continuar();
                } else {
                    fernan.borrarResrbaById(r.getId(), usuarioLogueado);
                }
            }
        }
    }

    private static void verReservasUsuario(FernanByB fernan, String usuarioLogueado) {
        if (fernan.getUsuarioById(usuarioLogueado).tieneReservas()) System.out.println("No tienes reservas");
        else {
            if (fernan.getUsuarioById(usuarioLogueado).getReserva1() != null)
                System.out.println(fernan.getUsuarioById(usuarioLogueado).getReserva1());
            if (fernan.getUsuarioById(usuarioLogueado).getReserva2() != null)
                System.out.println(fernan.getUsuarioById(usuarioLogueado).getReserva2());
        }
    }

    private static void bloqueaFechaVivienda(FernanByB fernan, String usuarioLogueado) {
        System.out.println("Menú para establecer un periodo de tu vivienda como no reservable, " +
                "inserta la fecha de inicio y la fecha de fin de ese periodo.");
        LocalDate fini = null, ffin = null;
        do {
            try {
                System.out.print("Fecha de inicio: ");
                fini = LocalDate.parse(s.nextLine(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            } catch (DateTimeException e) {
                System.out.println("No has intoducido bien el fomrato de la fecha, (el correcto es dd-mm-yyyy)");
            }
        } while (fini == null);
        do {
            try {
                System.out.print("Fecha de fin: ");
                ffin = LocalDate.parse(s.nextLine(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            } catch (DateTimeException e) {
                System.out.println("No has intoducido bien el fomrato de la fecha, (el correcto es dd-mm-yyyy)");
            }
        } while (ffin == null);
        if (fernan.bloqueaVivienda(fini, ffin, fernan.getPropietarioById(usuarioLogueado).getVivienda()))
            System.out.println("La vivienda ha sido bloqueada correctamente");
        else System.out.println("La vivienda no ha podido ser bloqueada");
    }

    private static void creaEditaVivienda(FernanByB fernan, String usuarioLogueado) {
        if (fernan.getPropietarioById(usuarioLogueado).getVivienda() == null) crearVivienda(fernan, usuarioLogueado);
        else editarVivienda(fernan, usuarioLogueado);
    }

    private static void editarVivienda(FernanByB fernan, String usuarioLogueado) {
        System.out.println("Bienvenido al menú de edición de vivienda");
        System.out.println("¿Quieres cambiar la vivienda?(s/n)");
        String nombre = "";
        if (s.nextLine().toLowerCase().charAt(0) == 's') {
            System.out.println("Inserta el nuevo nombre de la vivienda");
            nombre = s.nextLine();
        }
        double precioNoche = 0;
        System.out.println("¿Quieres cambiar el precio por noche de la vivienda?(s/n)");
        if (s.nextLine().toLowerCase().charAt(0) == 's') {
            System.out.println("Inserta el nuevo nombre de la vivienda");
            do {
                try {
                    precioNoche = Double.parseDouble(s.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Por favor introduce un número");
                }
            } while (precioNoche == 0);
        }
        int huespedes = 0;
        System.out.println("¿Quieres cambiar el número de huespedes de la vivienda?");
        if (s.nextLine().toLowerCase().charAt(0) == 'S') {
            System.out.println("Inserta el nuevo nombre de la vivienda");
            do {
                try {
                    huespedes = Integer.parseInt(s.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Por favor introduce un número");
                }
            } while (huespedes == 0);
        }
        fernan.editarVivienda(usuarioLogueado, nombre, precioNoche, huespedes);
    }

    private static void crearVivienda(FernanByB fernan, String usuarioLogueado) {
        System.out.println("Bienvenido al menú de creación de vivienda");
        System.out.print("Inserta el nombre de la vivienda:");
        String nombre = s.nextLine();
        System.out.println("Inserta la localidad de la vivienda");
        String localidad = s.nextLine();
        System.out.println("Inserta la calle de la vivineda");
        String calle = s.nextLine();
        System.out.println("Inserta el número de la vivienda");
        String numero = s.nextLine();
        System.out.println("Inserta el precio por noche de la vivienda");
        double precioNoche = 0;
        do {
            try {
                precioNoche = Double.parseDouble(s.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Introduce un valor válido");
            }
        } while (precioNoche == 0);
        System.out.println("Inserta el número de huespedes que admite la vivienda");
        int huespedes = 0;
        do {
            try {
                huespedes = Integer.parseInt(s.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Introduce un valor válido");
            }
        } while (huespedes == 0);
        fernan.nuevaVivienda(usuarioLogueado, nombre, localidad, calle, numero, precioNoche, huespedes);
    }

    private static void modificarUsuario(FernanByB fernan, String usuarioLogueado) {
        System.out.println("Vienvenido al menú de modificación de  usuario, estos son tus datos:");
        System.out.println(fernan.getPersonaById(usuarioLogueado));
        System.out.println("A continuación tendrás que ir introduciendo los parámetros que deseas cambiar, si no quieres cambiar alguno dejalo en blanco):");
        System.out.print("Nuevo nombre: ");
        String nombre = s.nextLine();
        System.out.println("Nuevo usuario: ");
        String user = s.nextLine();
        System.out.println("Nueva contraseña");
        String pass = s.nextLine();
        if (pass != "") {
            System.out.println("Para cambiar tu contraseña, primero tienes que cambiar tu contraseña anterior:");
            System.out.print("Tu contraseña anterior: ");
            if (!fernan.claveCorrecta(usuarioLogueado, pass)) {
                System.out.println("Ha habido un error al comprobar la clave");
                pass = "";
            }
        }
        System.out.println("Nuevo correo");
        String mail = s.nextLine();
        if (!validaEmail(mail)) {
            System.out.println("El email insertado es incorrecto");
            mail = "";
        }
        fernan.modificausuario(nombre, user, pass, mail, usuarioLogueado);
        System.out.println("Su usuario ha sido modificado correctamente");
        continuar();
    }

    private static void verReservasMiVivienda(FernanByB fernan, String usuarioLogueado) {
        pintaReservasVivienda(fernan.getPropietarioById(usuarioLogueado).getVivienda().getReservas(), fernan, usuarioLogueado);
    }

    private static void pintaReservasVivienda(String reservas, FernanByB fernan, String usuarioLogueado) {
        if (reservas.length() == 1)
            System.out.println(reservas.equals("1") ? fernan.getPropietarioById(usuarioLogueado).getVivienda().getReserva1() : fernan.getPropietarioById(usuarioLogueado).getVivienda().getReserva2());
        else {
            System.out.println(fernan.getPropietarioById(usuarioLogueado).getVivienda().getReserva1());
            System.out.println(fernan.getPropietarioById(usuarioLogueado).getVivienda().getReserva2());
        }
    }

    private static void verViviendasPropietario(FernanByB fernan, String usuarioLogueado) {
        System.out.println(fernan.getViviendaByPropietarioId(usuarioLogueado));
    }

    public static void registroUsuario(FernanByB fernan) {
        if (fernan.usuariosLlenos()) System.out.println("No se puede crear un usuario porque se ha alcanzado el maxmo");
        else {
            System.out.println("Menu de creación de usuario, inserta los campos segun se van pidiendo");
            System.out.print("Nombre: ");
            String nombre = s.nextLine();
            System.out.print("Usuario: ");
            String usuario = s.nextLine();
            System.out.print("Clave: ");
            String pass = s.nextLine();
            System.out.print("DNI: ");
            String dni = s.nextLine();
            System.out.print("E-Mail: ");
            String mail = s.nextLine();
            System.out.println(crearUsuario(dni, mail, nombre, usuario, pass, fernan) ? "Usuario creado." : "No se ha podido crear el usuario.");
        }
        continuar();
    }

    public static void registroPropietario(FernanByB fernan) {
        if (fernan.propietariosLlenos())
            System.out.println("No se puede crear un propietario porque se ha alcanzado el naximo.");
        else {
            System.out.println("Menu de creacion de propietario, inserta los campos segun se van pidiendo");
            System.out.print("Nombre: ");
            String nombre = s.nextLine();
            System.out.print("Usuario: ");
            String usuario = s.nextLine();
            System.out.print("Clave: ");
            String pass = s.nextLine();
            System.out.print("DNI: ");
            String dni = s.nextLine();
            System.out.print("E-Mail: ");
            String mail = s.nextLine();
            System.out.println(crearPropietario(dni, mail, nombre, usuario, pass, fernan) ? "Propietario creado." : "No se ha podido crear el propietario");
        }
        continuar();
    }

    public static void registroAdmin(FernanByB fernan) {
        if (fernan.adminLleno()) System.out.println("No se puede crear un admin porque se ha llegado al número máximo");
        else {
            System.out.println("Para crear un administrador primero tienes que insertar la clave correspondiente");
            if (!s.nextLine().equals("f3m")) System.out.println("Contraseña incorrecta.");
            else {
                System.out.println("Menu de creación de admin, inserta los campos según se piden");
                System.out.print("Nombre: ");
                String nombre = s.nextLine();
                System.out.print("Usuario: ");
                String usuario = s.nextLine();
                System.out.print("Clave: ");
                String pass = s.nextLine();
                System.out.print("DNI: ");
                String dni = s.nextLine();
                System.out.print("E-Mail: ");
                String mail = s.nextLine();
                System.out.println(crearAdmin(dni, mail, nombre, usuario, pass, fernan) ? "Admin creado" : "No se ha podido crear el admin");
            }
            continuar();
        }
    }

    public static String login(FernanByB fernan, String usuarioLogueado) {
        System.out.println("Inserta el usuario con el que quiere loguearte");
        String user = s.nextLine();
        System.out.println("Ahora inserta la contraseña con la que quieres loguearte");
        String pass = s.nextLine();
        usuarioLogueado = fernan.login(user, pass);
        if (usuarioLogueado.equals("")) System.out.println("Credenciales incorrectas.");
        else System.out.println("Te has logueado correctamente");
        continuar();
        return usuarioLogueado;
    }

    public static void viviendasEnAlquiler(FernanByB fernan) {
        pintarViviendas(fernan.viviendasEnAlquiler(), fernan);
    }

    public static void pintarViviendas(String viviendas, FernanByB fernan) {
        if (viviendas.length() == 0) System.out.println("No hay viviendas en el sistema");
        else {
            if (viviendas.length() == 1)
                System.out.println(viviendas.equals("1") ? fernan.getVivienda1() : fernan.getVivienda2());
            else {
                System.out.println(fernan.getVivienda1());
                System.out.println(fernan.getVivienda2());
            }
        }
        continuar();
    }

    public static void usuariosSistema(FernanByB fernan) {
        pintarUsuarios(fernan.usuariosSistema(), fernan);
    }

    public static void pintarUsuarios(String usuarios, FernanByB fernan) {
        if (usuarios.length() == 0) System.out.println("No hay ningun usuario en el sistema");
        else {
            if (usuarios.length() == 1)
                System.out.println(usuarios.equals("1") ? fernan.getUsuario1() : fernan.getUsuario2());
            else {
                System.out.println(fernan.getUsuario1());
                System.out.println(fernan.getUsuario2());
            }
        }
        continuar();
    }

    public static void reservasSistema(FernanByB fernan) {
        pintaReservas(fernan.reservasSistema(), fernan);
    }

    public static void pintaReservas(String reservas, FernanByB fernan) {
        for (int i = 0; i < reservas.length() - 1; i++) {
            switch (reservas.charAt(0)) {
                case 1 -> System.out.println(fernan.getReserva1());
                case 2 -> System.out.println(fernan.getReserva2());
                case 3 -> System.out.println(fernan.getReserva3());
                case 4 -> System.out.println(fernan.getReserva4());
            }
            reservas = reservas.substring(1, reservas.length() - 1);
        }
    }

    public static void pintaPersona(FernanByB fernan, String usuarioLogueado) {
        System.out.println(fernan.getPersonaById(usuarioLogueado));
    }

    public static void pintaMenu(FernanByB fernan, String usuarioLogueado) {
        if (usuarioLogueado.equals("")) System.out.println("""
                ==============================================
                           Bienvenido a FernanB&B           
                ==============================================
                  Que quieres hacer                         
                  1.-Loguearme                              
                  2.-Registrarme como usuario               
                  3.-Registrarme como propietario           
                  4.-Registrarme como administrador         
                ==============================================
                """);
        else {
            switch (usuarioLogueado.charAt(0)) {
                case '1' -> System.out.printf("""
                        =============================================
                                                                   
                         Menu de admin                             
                         Bienvenido: %s                            
                        =============================================%s
                         1.-Ver todas las viviendas en alquiler    
                         2.-Ver todos los usuarios del sistema        
                         3.-Ver todas las reservas de viviendas       
                         4.-Ver mi perfil                          
                         5.-Modificar mi perfil                       
                         6.-Cerrar sesión                          
                         ============================================                                           
                        """, fernan.getAdminById(usuarioLogueado).getNombre(), fernan.getAdminById(usuarioLogueado).verificado() ? "" : "\n  0.-Verificar mi cuenta");
                case '2' -> System.out.printf("""
                        ================================================================
                                                                                      
                          Menu de propietario                                         
                          Bienvenido: %s                                              
                        ================================================================%s
                          1.-Ver mis viviendas en alquiler                            
                          2.-Crear/Editar mis viviendas                               
                          3.-Ver las reservas de mis viviendas                        
                          4.-Establecer un periodo de no disponible para una vivienda 
                          5.-Ver mi perfil                                            
                          6.-Modificar mi perfil                                      
                          7.-Cerrar sesión                                            
                                                                                      
                        ================================================================
                        """, fernan.getPropietarioById(usuarioLogueado).getNombre(), fernan.getPropietarioById(usuarioLogueado).verificado() ? "" : "\n  0.-Verificar mi usuario");
                case '3' -> System.out.printf("""
                        ==========================================
                                                                 
                          Menu de usuario                       
                          Bienvenido: %s                        
                        ==========================================%s
                          1.-Busqueda de alojamientos           
                          2.-Ver mis reservas                   
                          3.-Modificar mis reservas             
                          4.-Ver mi perfil                      
                          5.-Modificar mi perfil                
                          6.-Cerrar sesión                      
                                                                  
                        ==========================================
                        """, fernan.getUsuarioById(usuarioLogueado).getnombre(), fernan.getUsuarioById(usuarioLogueado).verificado() ? "" : "\n  0.-Verificar mi usuario");
            }
        }
    }

    public static boolean crearPropietario(String dni, String mail, String nombre, String user, String pass, FernanByB fernan) {
        boolean created = false;
        if (validaEmail(mail) && validaDni(dni)) {
            String token = generarToken();
            if (fernan.creaPropietario(dni.toUpperCase(), mail, user, pass, nombre, token)) created = true;
            enviarToken(token, mail);
            tokenEnviado();
        }
        return created;
    }

    public static boolean crearUsuario(String dni, String mail, String nombre, String user, String pass, FernanByB fernan) {
        boolean created = false;
        if (validaEmail(mail) && validaDni(dni)) {
            String token = generarToken();
            if (fernan.creaUsuario(dni.toUpperCase(), mail, user, pass, nombre, token)) created = true;
            enviarToken(token, mail);
            tokenEnviado();
        }
        return created;
    }

    public static boolean crearAdmin(String dni, String mail, String nombre, String user, String pass, FernanByB fernan) {
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

    public static void verificarUsuario(FernanByB fernan, String id) {
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