package ua.rainbow.exam.time;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;

import static java.lang.System.out;


public class DateTime {
    private static void instantTime() {
        out.println("Test of instant plus 200 miles");
        out.println(Instant.now().plusMillis(200));
        out.println("Test of instant plus period of 1 day");
        out.println(Instant.now().plus(Period.ofDays(1)));
        out.println("Test of instant plus duration of day");
        out.println(Instant.now().plus(Duration.ofDays(1)));
        out.println("Test of instant 1 days chrono unit");
        out.println(Instant.now().plus(1, ChronoUnit.DAYS));
    }

    private static void chronoUnits() {
        out.println("Difference between LD#now#plus#1 and LDT#now");
        out.print(ChronoUnit.DAYS.between(LocalDate.now().plus(Period.ofDays(1)), LocalDateTime.now()));
    }

    private static void formattingAny_shit_thing() {
        LocalDate date = LocalDate.of(2020, Month.JANUARY, 20);
        LocalTime time = LocalTime.of(11, 12, 34);
        LocalDateTime dateTime = LocalDateTime.of(date, time);
        System.out.println("Date formats");

        System.out.println(date.format(DateTimeFormatter.ISO_LOCAL_DATE));
        System.out.println(time.format(DateTimeFormatter.ISO_LOCAL_TIME));
        System.out.println(dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        System.out.println(dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE));
        System.out.println(dateTime.format(DateTimeFormatter.ISO_LOCAL_TIME));
    }

    private static void anotherWayOfDTFormatting() {
        LocalDate date = LocalDate.of(2020, Month.JANUARY, 20);
        LocalTime time = LocalTime.of(11, 12, 34);
        LocalDateTime dateTime = LocalDateTime.of(date, time);
        System.out.println("Date formats with Date formatter");
        DateTimeFormatter shortDateTime =
                DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
        System.out.println(dateTime.format(shortDateTime));
        System.out.println(date.format(shortDateTime));
        try {
            System.out.println("This 'System.out.println(time.format(shortDateTime));' should fail...");
            System.out.println(time.format(shortDateTime));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String... args) {
        chronoUnits();
        instantTime();
        formattingAny_shit_thing();
        anotherWayOfDTFormatting();
    }
}