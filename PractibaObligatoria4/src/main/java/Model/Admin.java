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
    public boolean verificado(){
        return this.token==null;
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
    public boolean claveCorrecta(String pass){
        return pass.equals(this.pass);
    }
    @Override
    public String toString() {
        return String.format("""
                ====================
                       Admin        
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

