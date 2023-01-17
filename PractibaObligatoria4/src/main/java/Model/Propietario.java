package Model;

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
    public String getid() {
        return this.id;
    }

    public String getUser() {
        return this.user;
    }

    public String getNombre() {
        return this.nombre;
    }
}

