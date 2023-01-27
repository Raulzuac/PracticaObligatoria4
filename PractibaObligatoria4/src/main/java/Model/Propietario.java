package Model;

import java.time.LocalDate;

public class Propietario {
    private String id;
    private String DNI;
    private String email;
    private String nombre;
    private String user;
    private String pass;
    private String token;
    private Vivienda vivienda;

    public Propietario(String id, String DNI, String email, String nombre, String user, String pass,String token) {
        this.id = id;
        this.DNI = DNI;
        this.email = email;
        this.nombre = nombre;
        this.user = user;
        this.pass = pass;
        this.token=token;
    }
    public boolean verificarPropietario(String token) {
        if (token.equals(this.token)){
            this.token=null;
            return true;
        }
        return false;
    }

    public Vivienda getVivienda() {
        if (this.vivienda==null)return null;
        return vivienda;
    }

    public String getid() {
        return this.id;
    }

    public String getUser() {
        return this.user;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setVivienda(Vivienda vivienda) {
        this.vivienda = vivienda;
    }

    public boolean verificado(){
        return this.token==null;
    }
    public boolean loginCorrecto(String user,String pass){
        return this.user.equals(user) && this.pass.equals(pass);
    }
    public boolean claveCorrecta(String pass){return pass.equals(this.pass);}
    @Override
    public String toString() {
        return String.format("""
                ====================
                    Propietario     
                ====================
                Nmobre: %s
                Usuario: %s
                DNI: %s
                E-Mail: %s
                Verificado: %s
                ====================
                """,this.nombre,this.user,this.DNI,this.email,(this.token==null)?"Si":"No");
    }

    public void modificar(String nombre, String user, String pass, String mail) {
        if (!nombre.equals(""))this.nombre=nombre;
        if (!user.equals(""))this.user=user;
        if (!pass.equals(""))this.pass=pass;
        if (!mail.equals(""))this.email=mail;
    }


}

