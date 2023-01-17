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

public class Main{
    public static Scanner s = new Scanner(System.in);
    public static FernanByB fernan= new FernanByB();
    public static void main(String[] args) {
        crearUsuario("12345678F","zucarraul@gmail.com","raul","rz","pass");
    }
    public static boolean crearPropietario(String dni,String mail,String nombre,String user,String pass){
        boolean created=false;
        if (validaEmail(mail) && validaDni(dni)){
            String token=generarToken();
            if (fernan.creaPropietario(dni.toUpperCase(),mail,user,pass,nombre,token))created=true;
            enviarToken(token,mail);
            tokenEnviado();
        }
        return created;
    }
    public static boolean crearUsuario(String dni,String mail,String nombre,String user,String pass){
        boolean created=false;
        if (validaEmail(mail) && validaDni(dni)){
            String token= generarToken();
            if (fernan.creaUsuario(dni.toUpperCase(),mail,user,pass,nombre,token))created=true;
            enviarToken(token,mail);
            tokenEnviado();
        }
        return created;
    }
    public static boolean crearAdmin(String dni,String mail,String nombre,String user,String pass){
        boolean created=false;
        if (validaEmail(mail) && validaDni(dni)){
            String token=generarToken();
            if (fernan.crearAdmin(dni.toUpperCase(),mail,user,pass,nombre,token))created=true;
            enviarToken(token,mail);
            tokenEnviado();
        }
        return created;
    }
    public static boolean validaEmail(String mail){
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = pattern.matcher(mail);

        return matcher.find();
    }
    public static boolean validaDni(String dni){
        boolean correct=true;
        if (dni.length()!=9)return false;
        else{
            for (int i = 0; i < 8; i++) {
                if (Character.isLetter(dni.charAt(i)))correct=false;
            }
            if (Character.isDigit(dni.charAt(8)))correct=false;
        }
        return correct;
    }
    public static String generarToken(){
        return String.valueOf((int)(Math.random()*10000));
    }
    public static void enviarToken(String token,String mail){
        Comms.enviarMensajeTelegram("El token para verificar tu usuario es: "+token);
        Comms.enviarConGMail(mail,"Tu token de fernanbyb",token);
    }
    public static void verificarUsuario(String id){
        System.out.print("Hola, "+fernan.getNombreById(id)+" estas intentando verificar tu cuenta, inserta a continuación el codigo que hemos enviado a tu correo o telegram.\n" +
                "token: ");
        System.out.println(fernan.verificarUsuario(id,s.nextLine())?"Usuario verificado correctamente.":"No se ha podido verificar tu usuario.");
        continuar();
    }
    public static void tokenEnviado(){
        System.out.println("Se ha enviado un token a tu correo electronico y a telegram, verificalo para poder acceder a todas las funcionalidades de FernanB&B");
    }
    public static void continuar(){
        System.out.print("Pulsa ENTER para continuar.");
        s.nextLine();
    }}