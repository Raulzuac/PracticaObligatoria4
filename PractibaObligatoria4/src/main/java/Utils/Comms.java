package Utils;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

public class Comms {
    public static boolean enviarMensajeTelegram(String mensaje){
        String direccion;
        String fijo="https://api.telegram.org/bot5853436517:AAFuZkbtdxI5bwuVlHlksF1k3iIpWlir5Ks/sendMessage?chat_id=296917431&text=";
        direccion=fijo+mensaje;
        URL url;
        boolean dev;
        dev=false;
        try{
            url=new URL(direccion);
            URLConnection con=url.openConnection();
            BufferedReader in= new BufferedReader(new InputStreamReader(con.getInputStream()));
            dev=true;
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return dev;
    }
    public static void enviarConGMail(String destinatario, String asunto, String cuerpo) {
        cuerpo=String.format("""
                <h1 style=" font-family: system-ui, -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif; text-align: center; color: lightcoral;">Verifica tu cuenta de FernanBnB</h1>
                <h3 style="font-family: system-ui, -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif; text-align: center;">Tu token de verificación es el siguiente:</h3>
                <p style="font-family: system-ui, -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif; text-align: center;">%s</p>
                <img style="width: 30%%; margin-left:35%% ;" src="https://1000marcas.net/wp-content/uploads/2020/01/Airbnb-Logotipo.jpg" alt="imagen">
                                                                   """,cuerpo);
        //La dirección de correo de envío
        String remitente = "pruebajavarz@gmail.com";
        //La clave de aplicación obtenida según se explica en este artículo:
        String claveemail = "pnmsferkaffjztov";

        Properties props = System.getProperties();
        props.put("mail.smtp.host", "smtp.gmail.com");  //El servidor SMTP de Google
        props.put("mail.smtp.user", remitente);
        props.put("mail.smtp.clave", claveemail);    //La clave de la cuenta
        props.put("mail.smtp.auth", "true");    //Usar autenticación mediante usuario y clave
        props.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP
        props.put("mail.smtp.port", "587"); //El puerto SMTP seguro de Google
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");


        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(remitente));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));   //Se podrían añadir varios de la misma manera
            message.setSubject(asunto);
            message.setContent(cuerpo,"text/html; charset=utf-8");
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", remitente, claveemail);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (MessagingException me) {
            me.printStackTrace();   //Si se produce un error
        }
    }
}
