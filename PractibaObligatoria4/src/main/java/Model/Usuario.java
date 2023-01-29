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
    private Reserva reserva2;

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

    public Reserva getReserva1() {
        return reserva1;
    }

    public Reserva getReserva2() {
        return reserva2;
    }

    public boolean verificado(){
        return this.token==null;
    }
    public boolean verificarUsuario(String token) {
        if (token.equals(this.token)){
            this.token=null;
            return true;
        }
        return false;
    }
    public boolean loginCorrecto(String user,String pass){
        return this.user.equals(user) && this.pass.equals(pass);

    }
    public boolean claveCorrecta(String pass){return pass.equals(this.pass);}
    @Override
    public String toString() {
        return String.format("""
                ====================
                      Usuario       
                ====================
                Nmobre: %s
                Usuario: %s
                DNI: %s
                E-Mail: %s
                Verificado: %s
                ====================
                """,this.nombre,this.user,this.DNI,this.email,(this.token==null)?"Si":"No");
    }

    public void modificar(String nombre, String user, String pass, String mail,String token) {
        if (!nombre.equals(""))this.nombre=nombre;
        if (!user.equals(""))this.user=user;
        if (!pass.equals(""))this.pass=pass;
        if (!mail.equals("")){
            this.email=mail;
            this.token=token;
        }
    }

    public String getEmail() {
        return email;
    }

    public boolean tieneReservas() {
        return reserva1==null && reserva2==null;
    }
    public Reserva getReservaById(String id){
        if (reserva1.getId().equals(id))return reserva1;
        if (reserva2.getId().equals(id))return reserva2;
        return null;
    }
    public boolean borrarReservaById(String id){
        if (reserva1.getId().equals(id)){
            reserva1=null;
        }
        if (reserva2.getId().equals(id)){
            reserva2=null;
        }
        return false;
    }

    public void setReserva(Reserva r) {
        if (reserva1==null)reserva1=r;
        else if (reserva2==null)reserva2=r;
    }
    public boolean reservasLlenas(){
        return reserva1!=null  && reserva2!=null;
    }
}
