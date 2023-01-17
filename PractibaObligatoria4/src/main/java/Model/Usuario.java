package Model;

public class Usuario {
    private String id;
    private String DNI;
    private String email;
    private String nombre;
    private String user;
    private String pass;
    private String token;
    private Reserva reserva1;
    private Reserva resreva2;

    public Usuario(String id, String DNI, String email, String user, String pass,String nombre,String token) {
        this.id = id;
        this.DNI = DNI;
        this.email = email;
        this.nombre= nombre;
        this.user = user;
        this.pass = pass;
        this.token=token;
    }

    public String getid() {
        return this.id;
    }

    public String getUser() {
        return this.user;
    }
    public String getnombre() {
        return this.nombre;
    }
    public boolean verificarUsuario(String token) {
        if (token.equals(this.token)){
            this.token=null;
            return true;
        }
        return false;
    }


}
