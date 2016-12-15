package ua.rainbow.exam.l10n;

import java.text.NumberFormat;
import java.util.Locale;

public class FormatNumbers {

    static void formatNumbersDependsOnLocale() {
        int attendeesPerYear = 3_200_000;
        int attendeesPerMonth = attendeesPerYear / 12;
        NumberFormat us = NumberFormat.getInstance(Locale.US);
        System.out.println(us.format(attendeesPerMonth));
        NumberFormat g = NumberFormat.getInstance(Locale.GERMANY);
        System.out.println(g.format(attendeesPerMonth));
        NumberFormat ca = NumberFormat.getInstance(Locale.CANADA_FRENCH);
        System.out.println(ca.format(attendeesPerMonth));
    }

    static void formatCurrencyDependsOnLocale() {
        int attendeesPerYear = 3_200_000;
        NumberFormat us = NumberFormat.getCurrencyInstance(Locale.US);
        System.out.println(us.format(attendeesPerYear));
        NumberFormat g = NumberFormat.getCurrencyInstance(Locale.GERMANY);
        System.out.println(g.format(attendeesPerYear));
        NumberFormat ca = NumberFormat.getCurrencyInstance(Locale.CANADA_FRENCH);
        System.out.println(ca.format(attendeesPerYear));
    }

    public static void main(String[] args) {
        System.out.println("Number format");
        formatNumbersDependsOnLocale();
        System.out.println("Currency format");
        formatCurrencyDependsOnLocale();
    }
}
