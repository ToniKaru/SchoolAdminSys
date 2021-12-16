package se.iths.vimton;

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
            if (year < 1880 || year > LocalDate.now().getYear()+1)
                throw new IllegalArgumentException();
        }

        public static void ssnInvalid(String ssn) {
            if (ssn.length() != 13 && !ssn.matches("\\d{8}-\\d{4}"))
                throw new IllegalArgumentException();
        }

        public static void emailInvalid(String email) {
            String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9\\+_-]+(\\.[A-Za-z0-9\\+_-]+)*@"
                    + "[^-][A-Za-z0-9\\+-]+(\\.[A-Za-z0-9\\+-]+)*(\\.[A-Za-z]{2,})$";
            if (!email.matches(regexPattern))
                throw new IllegalArgumentException();

        }



    }


}
