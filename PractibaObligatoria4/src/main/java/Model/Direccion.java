package Model;

public class Direccion {
    private String localidad;
    private String calle;
    private String numero;
    private String piso;
    private String puerta;

    public Direccion(String localidad, String calle, String numero, String piso, String puerta) {
        this.localidad = localidad;
        this.calle = calle;
        this.numero = numero;
        this.piso = piso;
        this.puerta = puerta;
    }

    public Direccion(String localidad, String calle, String numero) {
        this.localidad = localidad;
        this.calle = calle;
        this.numero = numero;
    }

    public String getLocalidad() {
        return localidad;
    }
}

