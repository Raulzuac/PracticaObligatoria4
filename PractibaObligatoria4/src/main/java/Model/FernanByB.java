package Model;

import Utils.Comms;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FernanByB {
    private Usuario usuario1;
    private Usuario usuario2;
    private Propietario propietario1;
    private Propietario propietario2;
    private Admin admin;


    public FernanByB() {

    }

    public boolean usuariosLlenos() {
        return usuario1 != null && usuario2 != null;
    }

    /**
     * Método que devuelve las viviendas que están en alquiler actualmente
     *
     * @return String con los numeros de las viviendas que estan en alquiler, devuelve 1 si
     * esta solo la primera, devuelve 2 si solo esta la segunda y 12 si estan ambas
     */
    public String viviendasEnAlquiler() {
        String viviendas = "";
        if (propietario1!=null && propietario1.getVivienda() != null) viviendas = viviendas + "1";
        if (propietario2!=null && propietario2.getVivienda() != null) viviendas = viviendas + "2";
        return viviendas;
    }

    public String usuariosSistema() {
        String usuarios = "";
        if (usuario1 != null) usuarios = usuarios + "1";
        if (usuario2 != null) usuarios = usuarios + "2";
        return usuarios;
    }

    public String reservasSistema() {
        String reservas = "";
        if (propietario1!=null && propietario1.getVivienda().getReserva1()!=null)reservas=reservas.concat("1");
        if (propietario1!=null && propietario1.getVivienda().getReserva2()!=null)reservas=reservas.concat("2");
        if (propietario2!=null && propietario2.getVivienda().getReserva1()!=null)reservas=reservas.concat("3");
        if (propietario2!=null && propietario2.getVivienda().getReserva2()!=null)reservas=reservas.concat("4");
        return reservas;
    }

    public void nuevaVivienda(String id, String nombre, String localidad, String calle, String numero, double precioNoche, int huespedes) {
        getPropietarioById(id).setVivienda(new Vivienda(nombre, new Direccion(localidad, calle, numero), precioNoche, huespedes));
        Comms.enviarMensajeTelegram("Se ha creado una vivienda Nueva: "+getPropietarioById(id).getVivienda().toStringTelegram());
        Comms.enviarConGMailCrearVivienda(getPropietarioById(id).getEmail(),"Creación de vivienda",nombre,localidad,calle,numero,precioNoche,String.valueOf(huespedes));
    }

    public void editarVivienda(String id, String nombre, double precionoche, int huespedes) {
        getPropietarioById(id).getVivienda().editarVivienda(nombre, precionoche, huespedes);
    }

    public boolean propietariosLlenos() {
        return propietario1 != null && propietario2 != null;
    }

    public boolean adminLleno() {
        return admin != null;
    }

    public boolean verificarUsuario(String id, String token) {
        if (usuario1 != null && usuario1.getid().equals(id) && usuario1.verificarUsuario(token)) return true;
        if (usuario2 != null && usuario2.getid().equals(id) && usuario2.verificarUsuario(token)) return true;
        if (propietario1 != null && propietario1.getid().equals(id) && propietario1.verificarPropietario(token))
            return true;
        if (propietario2 != null && propietario2.getid().equals(id) && propietario2.verificarPropietario(token))
            return true;
        return admin != null && admin.getid().equals(id) && admin.verificarAdmin(token);
    }

    public boolean creaUsuario(String dni, String mail, String user, String pass, String nombre, String token) {
        boolean usuariocreado = false;
        if ((usuario1 == null || !usuario1.getUser().equals(user)) && (usuario2 == null || !usuario2.getUser().equals(user))) {
            if (usuario1 == null) {
                usuario1 = new Usuario(generaIdUsuario(), dni, mail, user, pass, nombre, token);
                usuariocreado = true;
            } else if (usuario2 == null) {
                usuario2 = new Usuario(generaIdUsuario(), dni, mail, user, pass, nombre, token);
                usuariocreado = true;
            }
        }
        return usuariocreado;
    }

    public boolean creaPropietario(String dni, String mail, String user, String pass, String nombre, String token) {
        boolean propietarioCreado = false;

        if ((propietario1 == null || !propietario1.getUser().equals(user)) && (propietario2 == null || !propietario2.getUser().equals(user))) {
            if (propietario1 == null) {
                propietario1 = new Propietario(generaIdPropietario(), dni, mail, nombre, user, pass, token);
                propietarioCreado = true;
            } else if (propietario2 == null) {
                propietario2 = new Propietario(generaIdPropietario(), dni, mail, nombre, user, pass, token);
                propietarioCreado = true;
            }
        }
        return propietarioCreado;
    }

    public boolean crearAdmin(String dni, String mail, String user, String pass, String nombre, String token) {
        boolean adminCreado = false;
        if (admin == null) {
            admin = new Admin(generaIdAdmin(), dni, mail, nombre, user, pass, token);
        }
        return adminCreado;
    }

    public String generaIdUsuario() {
        boolean correct = false;
        String id;
        do {
            id = "3" + (int) (Math.random() * 10000);
            if (usuario1 == null && usuario2 == null) correct = true;
            else if (usuario1 != null && id != usuario1.getid()) correct = true;
            else if (usuario2 != null && id != usuario2.getid()) correct = true;
        } while (!correct);
        return id;
    }

    public String generaIdPropietario() {
        boolean correct = false;
        String id;
        do {
            id = "2" + (int) (Math.random() * 10000);
            if (propietario1 == null && propietario2 == null) correct = true;
            else if (propietario1 != null && id != propietario1.getid()) correct = true;
            else if (propietario2 != null && id != propietario2.getid()) correct = true;
        } while (!correct);
        return id;
    }

    public String generaIdAdmin() {
        String id = "1" + (int) (Math.random() * 10000);
        return id;
    }

    public String generaIdReserva() {
        int idreserva;
        do {
            idreserva = (int) (Math.random() * 899999) + 100000;
        }while (!idReservaNoCoincide(idreserva));
        return String.valueOf(idreserva);
    }

    private boolean idReservaNoCoincide(int idreserva) {
        String id=String.valueOf(idreserva);
        boolean noCoincide=true;
        if (propietario1!=null){
            if (propietario1.getVivienda().getReserva1()!=null && propietario1.getVivienda().getReserva1().getId().equals(id))noCoincide=false;
            if (propietario1.getVivienda().getReserva2()!=null && propietario1.getVivienda().getReserva2().getId().equals(id))noCoincide=false;
        }
        if (propietario2!=null){
            if (propietario2.getVivienda().getReserva1()!=null && propietario1.getVivienda().getReserva1().getId().equals(id))noCoincide=false;
            if (propietario2.getVivienda().getReserva2()!=null && propietario1.getVivienda().getReserva2().getId().equals(id))noCoincide=false;
        }
        return noCoincide;
    }

    public String login(String user, String pass) {
        if (usuario1 != null && usuario1.loginCorrecto(user, pass)) return usuario1.getid();
        if (usuario2 != null && usuario2.loginCorrecto(user, pass)) return usuario2.getid();
        if (propietario1 != null && propietario1.loginCorrecto(user, pass)) return propietario1.getid();
        if (propietario2 != null && propietario2.loginCorrecto(user, pass)) return propietario2.getid();
        if (admin != null && admin.loginCorrecto(user, pass)) return admin.getid();
        return "";
    }

    public String getNombreById(String id) {
        if (usuario1 != null && usuario1.getid().equals(id)) return usuario1.getnombre();
        if (usuario2 != null && usuario2.getid().equals(id)) return usuario2.getnombre();
        if (propietario1 != null && propietario1.getid().equals(id)) return propietario1.getNombre();
        if (propietario2 != null && propietario2.getid().equals(id)) return propietario2.getNombre();
        if (admin != null && admin.getid().equals(id)) return admin.getNombre();
        return "";
    }

    public Usuario getUsuarioById(String id) {
        if (usuario1 != null && usuario1.getid().equals(id)) return usuario1;
        if (usuario2 != null && usuario2.getid().equals(id)) return usuario2;
        return null;
    }

    public Propietario getPropietarioById(String id) {
        if (propietario1 != null && propietario1.getid().equals(id)) return propietario1;
        if (propietario2 != null && propietario2.getid().equals(id)) return propietario2;
        return null;
    }
    public Propietario propietarioByVivienda(Vivienda v){
        if (propietario1!=null && v.getNombre().equals(propietario1.getVivienda().getNombre()))return propietario1;
        if (propietario2!=null && v.getNombre().equals(propietario2.getVivienda().getNombre()))return propietario2;
        return null;
    }

    public Admin getAdminById(String id) {
        if (admin != null && admin.getid().equals(id)) return admin;
        return null;
    }

    public Vivienda getVivienda1() {
        return propietario1.getVivienda();
    }

    public Vivienda getVivienda2() {
        return propietario2.getVivienda();
    }

    public Vivienda getViviendaByPropietarioId(String id) {
        if (id.equals(propietario1.getid())) return propietario1.getVivienda();
        return propietario2.getVivienda();
    }

    public Usuario getUsuario1() {
        return usuario1;
    }

    public Usuario getUsuario2() {
        return usuario2;
    }

    public Object getPersonaById(String id) {
        if (usuario1 != null && usuario1.getid().equals(id)) return usuario1;
        if (usuario2 != null && usuario2.getid().equals(id)) return usuario2;
        if (propietario1 != null && propietario1.getid().equals(id)) return propietario1;
        if (propietario2 != null && propietario2.getid().equals(id)) return propietario2;
        if (admin != null && admin.getid().equals(id)) return admin;
        return null;
    }

    public boolean claveCorrecta(String id, String pass) {
        if (getAdminById(id) != null) return getAdminById(id).claveCorrecta(pass);
        if (getPropietarioById(id) != null) return getPropietarioById(id).claveCorrecta(pass);
        if (getUsuarioById(id) != null) return getUsuarioById(id).claveCorrecta(pass);
        return false;
    }

    public void modificausuario(String nombre, String user, String pass, String mail, String id,String token) {
        if (getAdminById(id) != null) getAdminById(id).modificar(nombre, user, pass, mail,token);
        if (getPropietarioById(id) != null) getPropietarioById(id).modificar(nombre, user, pass, mail,token);
        if (getUsuarioById(id) != null) getUsuarioById(id).modificar(nombre, user, pass, mail,token);

    }

    public boolean bloqueaVivienda(LocalDate fini, LocalDate ffin, Vivienda v) {
        return v.reservar(fini, ffin, generaIdReserva())!=null;
    }
    public void reservaVivienda(LocalDate fini, LocalDate ffin, Vivienda v,String id){
        Reserva r= v.reservar(fini,ffin,generaIdReserva());
        getUsuarioById(id).setReserva(r);
        Comms.enviarMensajeTelegram(pintaReservaTelegram(r,v));
        Comms.enviarConGMailNuevaReservaPropietario(propietarioByVivienda(v).getEmail(),"Han reservado tu vivienda",r.getId(),r.getFini().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),r.getFfin().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),getUsuarioById(id).getnombre());
        Comms.enviarConGMailNuevaReservaUsuario(getUsuarioById(id).getEmail(),"Has realizado una reserva",v.getNombre(),r.getFini().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),r.getFfin().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
    }

    public boolean verificado(String id) {
        if (usuario1.getid().equals(id)) return usuario1.verificado();
        if (usuario2.getid().equals(id)) return usuario2.verificado();
        if (propietario1.getid().equals(id)) return propietario1.verificado();
        if (propietario2.getid().equals(id)) return propietario2.verificado();
        if (admin.getid().equals(id)) return admin.verificado();
        return false;
    }

    public Vivienda getViviendaByReservaId(String id) {
        if (propietario1.getVivienda().hasResrva(id)) return propietario1.getVivienda();
        if (propietario2.getVivienda().hasResrva(id)) return propietario2.getVivienda();
        return null;
    }

    public boolean borrarResrbaById(String id, String idUsuario) {
        Usuario u;
        Vivienda v;
        if (usuario1.getid().equals(idUsuario)) u = usuario1;
        else u = usuario2;
        v = getViviendaByReservaId(id);
        if (v == null) return false;
        else {
            u.borrarReservaById(id);
            v.borrarReservaById(id);
            return true;
        }
    }

    public String buscaVivienda(LocalDate fini, LocalDate ffin, String localidad) {
        String viviendasCoincidentes = "";
        if (propietario1!=null && propietario1.getVivienda().getLocalidad().equalsIgnoreCase(localidad))
            viviendasCoincidentes = viviendasCoincidentes + "1";
        if (propietario2!=null && propietario2.getVivienda().getLocalidad().equalsIgnoreCase(localidad))
            viviendasCoincidentes = viviendasCoincidentes + "2";
        return viviendasCoincidentes;
    }

    public Propietario getPropietario1() {
        return propietario1;
    }

    public Propietario getPropietario2() {
        return propietario2;
    }

    public String  pintaReserva(int i) {
        Propietario p=(i<=2)?propietario1:propietario2;
        Reserva r=(i%2!=0)?p.getVivienda().getReserva1():p.getVivienda().getReserva2();
        return (String.format("""
                ================================
                Vivienda:%s
                %s
                """,p.getVivienda().getNombre(),r));
    }
    public String  pintaReservaTelegram(Reserva r,Vivienda v) {

        return (String.format("================================%%0AVivienda:%s%%0A%s",v.getNombre(),r.toStringTelegram()));
    }

    public String pintaReservaPropietario(int i,Propietario p) {
        Reserva r;
        if (i==1)r=p.getVivienda().getReserva1();
        else r=p.getVivienda().getReserva2();
        return (String.format("""
                ================================
                Vivienda:%s
                %s
                """,p.getVivienda().getNombre(),r));
    }
}
