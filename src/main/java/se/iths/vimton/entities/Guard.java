package se.iths.vimton.entities;


import java.time.LocalDate;

public class Guard {

    public static class Against {

        public static void Empty(String object){
            if (object.trim().isEmpty())
                throw new IllegalArgumentException("Input value is empty.");
        }

        public static <T> void Null(T object){
            if (object == null)
                throw new IllegalArgumentException("Input value is null.");
        }

        public static void zeroOrLess(int number){
            if (number <= 0)
                throw new IllegalArgumentException("Input value is zero or less.");
        }

        public static void yearOutsideValidRange(int year){
            if (year < 1900 || year > LocalDate.now().getYear()+1)
                throw new IllegalArgumentException(year + " is outside of the valid range 1900 - current year");
        }

        public static void ssnInvalid(String ssn) {
            if (ssn.length() != 13 && !ssn.matches("\\d{8}-\\d{4}"))
                throw new IllegalArgumentException(ssn + " is an invalid ssn.");
            String date = ssn.substring(0,4) + "-" + ssn.substring(4, 6) + "-" + ssn.substring(6, 8);
            dateInvalid(date);
        }

        public static void dateInvalid(String date){
            if (date.length() != 10 && !date.matches("\\d{4}-\\d{2}-\\d{2}"))
                throw new IllegalArgumentException(date + " is an invalid date.");
            LocalDate localDate = LocalDate.parse(date);
            yearOutsideValidRange(localDate.getYear());
        }

        public static void emailInvalid(String email) {
            String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9\\+_-]+(\\.[A-Za-z0-9\\+_-]+)*@"
                    + "[^-][A-Za-z0-9\\+-]+(\\.[A-Za-z0-9\\+-]+)*(\\.[A-Za-z]{2,})$";
            if (!email.matches(regexPattern))
                throw new IllegalArgumentException(email + " is an invalid email address.");

        }

    }

}
