package Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

    @Override
    public String toString() {
        return String.format("""
                ================================
                Reserva id:%s
                Fecha inicio: %s
                Fecha fin: %s
                ================================
                """,this.id,fini.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),ffin.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
    }
    public String toStringTelegram() {
        String fini=this.fini.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String ffin=this.ffin.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        return String.format("================================%%0AReserva id:%s%%0AFecha inicio: %s%%0AFecha fin: %s%%0A" +
                "================================"
                ,this.id,fini,ffin);
    }
}

