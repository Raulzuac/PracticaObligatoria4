package Utils;

import java.time.LocalDate;

public class DateCalcs {
    public static boolean noCoinciden(LocalDate fini1,LocalDate fini2,LocalDate ffin1,LocalDate ffin2){
        return  (((fini1.isBefore(fini2))&& (ffin1.isBefore(fini2)))&&(fini1.isAfter(fini2))&& (ffin1.isAfter(fini2)));
    }
}
