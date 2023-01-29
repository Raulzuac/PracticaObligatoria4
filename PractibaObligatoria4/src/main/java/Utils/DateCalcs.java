package Utils;

import java.time.LocalDate;

public class DateCalcs {
    public static boolean noCoinciden(LocalDate fini1,LocalDate fini2,LocalDate ffin1,LocalDate ffin2){
        LocalDate aux;
        if (!fini1.isBefore(ffin1)){
            aux=fini1;
            fini1=ffin1;
            ffin1=aux;
        }
        if (!fini2.isBefore(ffin2)){
            aux=fini2;
            fini2=ffin2;
            ffin2=aux;
        }
        return  fini1.isAfter(ffin2) || ffin1.isBefore(fini2);
    }
}
