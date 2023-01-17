package Model;

public class Vivienda {
    private String nombre;
    private String id;
    private Direccion direccion;
    private Reserva reserva1;
    private Reserva reserva2;
    private double precioNoche;
    private int maxHuespedes;

    public Vivienda(String nombre, String id, Direccion direccion, Reserva reserva1, Reserva reserva2, double precioNoche, int maxHuespedes) {
        this.nombre = nombre;
        this.id = id;
        this.direccion = direccion;
        this.reserva1 = reserva1;
        this.reserva2 = reserva2;
        this.precioNoche = precioNoche;
        this.maxHuespedes = maxHuespedes;
    }
}

