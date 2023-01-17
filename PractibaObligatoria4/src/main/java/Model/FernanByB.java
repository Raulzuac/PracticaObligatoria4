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
            admin=new Admin(generaidAdmin(),dni,mail,nombre,user,pass,token);
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
    public String generaidAdmin(){
        String id="1"+(int)(Math.random()*10000);
        return id;
    }
    public String getNombreById(String id){
        if (usuario1!=null && usuario1.getid().equals(id))return usuario1.getnombre();
        if (usuario2!=null && usuario2.getid().equals(id))return usuario2.getnombre();
        if (propietario1!=null && propietario1.getid().equals(id))return propietario1.getNombre();
        if (propietario2!=null && propietario2.getid().equals(id))return propietario2.getNombre();
        if (admin!=null && admin.getid().equals(id))return admin.getNombre();
        return "";
    }

}

