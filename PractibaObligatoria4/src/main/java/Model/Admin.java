package Model;

public class Admin {
    private String id;
    private String DNI;
    private String email;
    private String nombre;
    private String user;
    private String pass;
    private String token;

    public Admin(String id, String DNI, String email, String nombre, String user, String pass,String token) {
        this.id = id;
        this.DNI = DNI;
        this.email = email;
        this.nombre = nombre;
        this.user = user;
        this.pass = pass;
        this.token=token;
    }
    public boolean verificarAdmin(String token){
        if (token.equals(this.token)){
            this.token=null;
            return true;
        }
        return false;
    }

    public String getid() {
        return this.id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public boolean loginCorrecto(String user, String pass) {
        return this.user.equals(user) && this.pass.equals(pass);
    }
}

