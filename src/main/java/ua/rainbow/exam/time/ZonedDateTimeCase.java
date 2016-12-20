package ua.rainbow.exam.time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static java.lang.System.out;


public class ZonedDateTimeCase {

    private static void findUATimeZones() {
        out.println(String.format("System default time-zone is: %s", ZoneId.systemDefault()));
        out.println();
        out.println("Lookup for another Ukrainians time-zones...");
        ZoneId.getAvailableZoneIds()
                .stream()
                .filter(z -> z.contains("UA") || z.contains("Kiev"))
                .sorted().forEach(out::println);
    }

    private static void timeTravaling() {
        LocalDate date = LocalDate.of(2017, Month.MARCH, 26);
        LocalDate date1 = LocalDate.of(2016, Month.OCTOBER, 30);
        LocalTime time = LocalTime.of(3, 30);
        LocalTime time1 = LocalTime.of(3, 30);
        LocalDateTime dateTime = LocalDateTime.of(date, time);
        LocalDateTime dateTime1 = LocalDateTime.of(date1, time1);
        ZonedDateTime zonedDateTime = ZonedDateTime.of(date, time, ZoneId.systemDefault());
        ZonedDateTime zonedDateTime1 = ZonedDateTime.of(date1, time1, ZoneId.systemDefault());
        out.print("LocalDateTime is ");
        out.println(dateTime);
        out.print("ZonedDateTime is ");
        out.println(zonedDateTime);
        out.print("LocalDateTime N is ");
        out.println(dateTime1);
        out.print("ZonedDateTime N is ");
        out.println(zonedDateTime1);
    }

    public static void main(String... args) {
        findUATimeZones();
        timeTravaling();
    }
}