package se.iths.vimton.entities;

import org.hibernate.type.LocalDateType;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class Guard {

    class Against {

        public static void NullOrEmpty(String object){
            if (object == null || object.isEmpty())
                throw new IllegalArgumentException();
        }

        public static <T> void Null(T object){
            if (object == null)
                throw new IllegalArgumentException();
        }

        public static void zeroOrLess(int number){
            if (number <= 0)
                throw new IllegalArgumentException();
        }

        public static void yearOutsideValidRange(int year){
            if (year < 1900 || year > LocalDate.now().getYear()+1)
                throw new IllegalArgumentException();
        }

        public static void ssnInvalid(String ssn) {
            if (ssn.length() != 13 && !ssn.matches("\\d{8}-\\d{4}"))
                throw new IllegalArgumentException();
            dateInvalid(ssn.substring(0,8));
        }

        public static void dateInvalid(String date){
            if (date.length() != 10 && (!date.matches("\\d{4}-\\d{2}-\\d{2}") ||  !date.matches("\\d{8}")))
                throw new IllegalArgumentException();
            LocalDate localDate = LocalDate.parse(date);
            yearOutsideValidRange(localDate.getYear());
        }

        public static void emailInvalid(String email) {
            String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9\\+_-]+(\\.[A-Za-z0-9\\+_-]+)*@"
                    + "[^-][A-Za-z0-9\\+-]+(\\.[A-Za-z0-9\\+-]+)*(\\.[A-Za-z]{2,})$";
            if (!email.matches(regexPattern))
                throw new IllegalArgumentException();

        }





    }


}
