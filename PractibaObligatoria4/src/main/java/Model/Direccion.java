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
    public boolean esPiso(){
        return this.piso!=null;
    }

    @Override
    public String toString() {
        if (esPiso()){
            return String.format("""
                Localidad: %s
                Calle: %s
                Número: %s
                Piso: %s
                Puerta: %s""",this.localidad,this.calle,this.numero,this.piso,this.puerta);
        }
        return String.format("""
                Localidad: %s
                Calle: %s
                Número: %s""",this.localidad,this.calle,this.numero);

    }
    public String toStringTelegram() {
        if (esPiso()){
            return String.format("Localidad: %s%%0ACalle: %s%%0ANúmero: %s%%0APiso: %s%%0APuerta: %s",this.localidad,this.calle,this.numero,this.piso,this.puerta);
        }
        return String.format("Localidad: %s%%0ACalle: %s%%0ANúmero: %s",this.localidad,this.calle,this.numero);

    }
}

