package Model;

import java.time.LocalDate;

public class Reserva {
    public String id;
    private LocalDate fini;
    private LocalDate ffin;

    public Reserva(String id, LocalDate fini, LocalDate ffin) {
        this.id = id;
        this.fini = fini;
        this.ffin = ffin;
    }

    public LocalDate getFini() {
        return fini;
    }

    public LocalDate getFfin() {
        return ffin;
    }

    public String getId() {
        return id;
    }
}

