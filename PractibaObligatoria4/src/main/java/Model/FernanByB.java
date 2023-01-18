package Model;

public class FernanByB {
    private Usuario usuario1;
    private Usuario usuario2;
    private Propietario propietario1;
    private Propietario propietario2;
    private Admin admin;
    private Vivienda vivienda1;
    private Vivienda vivienda2;
    private Reserva reserva1;
    private Reserva reserva2;
    private Reserva reserva3;
    private Reserva reserva4;

    public FernanByB(){

    }
    public boolean usuariosLlenos(){
        return usuario1!=null && usuario2!=null;
    }

    /**
     * Método que devuelve las viviendas que están en alquiler actualmente
     * @return String con los numeros de las viviendas que estan en alquiler, devuelve 1 si
     * esta solo la primera, devuelve 2 si solo esta la segunda y 12 si estan ambas
     */
    public String viviendasEnAlquiler(){
        String viviendas="";
        if (vivienda1!=null)viviendas=viviendas+"1";
        if (vivienda2!=null)viviendas=viviendas+"2";
        return viviendas;
    }
    public String usuariosSistema(){
        String usuarios="";
        if (usuario1!=null)usuarios=usuarios+"1";
        if (usuario2!=null)usuarios=usuarios+"2";
        return usuarios;
    }
    public boolean propietariosLlenos(){
        return propietario1!=null && propietario2!=null;
    }
    public boolean adminLleno(){
        return admin!=null;
    }
    public boolean verificarUsuario(String id,String token){
        if (usuario1!=null && usuario1.getid().equals(id) && usuario1.verificarUsuario(token))return true;
        if (usuario2!=null && usuario2.getid().equals(id) && usuario2.verificarUsuario(token))return true;
        if (propietario1!=null && propietario1.getid().equals(id) && propietario1.verificarPropietario(token))return true;
        if (propietario2!=null && propietario2.getid().equals(id) && propietario2.verificarPropietario(token))return true;
        return admin != null && admin.getid().equals(id) && admin.verificarAdmin(token);
    }
    public boolean creaUsuario(String dni,String mail,String user,String pass,String nombre,String token){
        boolean usuariocreado=false;
        if ((usuario1==null || !usuario1.getUser().equals(user)) && (usuario2==null || !usuario2.getUser().equals(user))){
            if (usuario1 == null) {
                usuario1 = new Usuario(generaIdUsuario(), dni, mail, user, pass,nombre,token);
                usuariocreado = true;
            } else if (usuario2 == null) {
                usuario2 = new Usuario(generaIdUsuario(), dni, mail, user, pass,nombre,token);
                usuariocreado = true;
            }
        }
        return usuariocreado;
    }
    public boolean creaPropietario(String dni, String mail, String user, String pass, String nombre,String token) {
        boolean propietarioCreado=false;
        if ((propietario1==null || !propietario1.getUser().equals(user)) && (propietario2==null || !propietario2.getUser().equals(user))){
            if (propietario1==null){
                propietario1=new Propietario(generaIdPropietario(),dni,mail,nombre,user,pass,token);
                propietarioCreado=true;
            }else if (propietario2==null){
                propietario2=new Propietario(generaIdPropietario(),dni,mail,nombre,user,pass,token);
                propietarioCreado=true;
            }
        }
        return propietarioCreado;
    }
    public boolean crearAdmin(String dni, String mail, String user, String pass, String nombre,String token) {
        boolean adminCreado=false;
        if (admin==null){
            admin=new Admin(generaIdAdmin(),dni,mail,nombre,user,pass,token);
        }
        return adminCreado;
    }
    public String generaIdUsuario(){
        boolean correct=false;
        String id;
        do {
            id= "3"+(int)(Math.random()*10000);
            if (usuario1==null && usuario2==null)correct=true;
            else if (usuario1!=null && id!=usuario1.getid())correct=true;
            else if (usuario2!=null && id!=usuario2.getid())correct=true;
        }while (!correct);
        return id;
    }
    public String generaIdPropietario(){
        boolean correct=false;
        String id;
        do {
            id= "2"+(int)(Math.random()*10000);
            if (propietario1==null && propietario2==null)correct=true;
            else if (propietario1!=null && id!=propietario1.getid())correct=true;
            else if (propietario2!=null && id!=propietario2.getid())correct=true;
        }while (!correct);
        return id;
    }
    public String generaIdAdmin(){
        String id="1"+(int)(Math.random()*10000);
        return id;
    }
    public String login(String user,String pass){
        if (usuario1!=null && usuario1.loginCorrecto(user,pass))return usuario1.getid();
        if (usuario2!=null && usuario2.loginCorrecto(user,pass))return usuario2.getid();
        if (propietario1!=null && propietario1.loginCorrecto(user,pass))return propietario1.getid();
        if (propietario2!=null && propietario2.loginCorrecto(user,pass))return propietario2.getid();
        if (admin!=null && admin.loginCorrecto(user,pass))return admin.getNombre();
        return "";
    }
    public String getNombreById(String id){
        if (usuario1!=null && usuario1.getid().equals(id))return usuario1.getnombre();
        if (usuario2!=null && usuario2.getid().equals(id))return usuario2.getnombre();
        if (propietario1!=null && propietario1.getid().equals(id))return propietario1.getNombre();
        if (propietario2!=null && propietario2.getid().equals(id))return propietario2.getNombre();
        if (admin!=null && admin.getid().equals(id))return admin.getNombre();
        return "";
    }
    public Usuario getUsuarioById(String id){
        if (usuario1!=null && usuario1.getid().equals(id))return usuario1;
        if (usuario2!=null && usuario2.getid().equals(id))return usuario2;
        return null;
    }
    public Propietario getPropietarioById(String id){
        if (propietario1!=null && propietario1.getid().equals(id))return propietario1;
        if (propietario2!=null && propietario2.getid().equals(id))return propietario2;
        return null;
    }
    public Admin getAdminById(String id){
        if (admin!=null && admin.getid().equals(id))return admin;
        return null;
    }

    public Vivienda getVivienda1() {
        return vivienda1;
    }

    public Vivienda getVivienda2() {
        return vivienda2;
    }

    public Usuario getUsuario1() {
        return usuario1;
    }

    public Usuario getUsuario2() {
        return usuario2;
    }
}
