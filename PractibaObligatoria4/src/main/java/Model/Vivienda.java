package Model;

import Utils.DateCalcs;

import java.time.LocalDate;

public class Vivienda {
    private String nombre;
    private String id;
    private Direccion direccion;
    private Reserva reserva1;
    private Reserva reserva2;
    private double precioNoche;
    private int maxHuespedes;
    private static int numViviendas=0;

    public Vivienda(String nombre, Direccion direccion, double precioNoche, int maxHuespedes) {
        this.nombre = nombre;
        this.id=String.valueOf(numViviendas);
        numViviendas++;
        this.direccion = direccion;
        this.precioNoche = precioNoche;
        this.maxHuespedes = maxHuespedes;
    }
    public Reserva getReserva1(){
        return reserva1;
    }

    public Reserva getReserva2() {
        return reserva2;
    }
    public String getReservas(){
        String reservas="";
        if (reserva1!=null)reservas=reservas+"1";
        if (reserva2!=null)reservas=reservas+"2";
        return reservas;
    }

    public void editarVivienda(String nombre, double precionoche, int huespedes) {
        if (!nombre.equals(""))this.nombre=nombre;
        if (precionoche!=0)this.precioNoche=precionoche;
        if (huespedes!=0)this.maxHuespedes=huespedes;
    }
    public boolean reservable(){
        return reserva1==null || reserva2==null;
    }
    public Reserva reservar(LocalDate fini,LocalDate ffin,String idreserva) {
        if (reserva1 == null && reserva2 == null){
            reserva1 = new Reserva(id, fini, ffin);
            return reserva1;
        }
        if (reserva1 == null && DateCalcs.noCoinciden(fini, reserva2.getFini(),ffin,reserva2.getFfin())){
            reserva1=new Reserva(id,fini,ffin);
            return reserva1;
        }
        if (reserva2==null && DateCalcs.noCoinciden(fini, reserva1.getFini(),ffin,reserva1.getFfin())){
            reserva2=new Reserva(id,fini,ffin);
            return reserva2;
        }
        return null;
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
    public boolean hasResrva(String id){
        if (reserva1.getId().equals(id))return true;
        if (reserva2.getId().equals(id))return true;
        return false;
    }
    public boolean reservableEn(LocalDate fini,LocalDate ffin){
        return  (DateCalcs.noCoinciden(fini,reserva1.getFini(),ffin,reserva2.getFfin()) || DateCalcs.noCoinciden(fini,reserva2.getFini(),ffin,reserva2.getFini()));
    }
    public String getLocalidad(){
        return direccion.getLocalidad();
    }
}

