package ua.rainbow.exam.l10n;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationCase {
    private static void getMyLocale() {
        Locale locale = Locale.getDefault();
        System.out.println(locale);
    }

    private static void buildOwn() {
        Locale.setDefault(new Locale.Builder().setLanguage("ua").setRegion("UA").build());

        Locale locale = Locale.getDefault();
        System.out.println(locale);
    }

    private static void playWithLocales(){
        Locale us = new Locale("en", "US");
        Locale france = new Locale("fr", "FR");
        Locale ukrainian = new Locale("ua", "UA");
        printProperties(us);
        System.out.println();
        printProperties(france);
        System.out.println();
        printProperties(ukrainian);
    }

    private static void printProperties(Locale locale) {
        ResourceBundle rb = ResourceBundle.getBundle("ua.rainbow.exam.l10n.Zoo", locale);
        System.out.println(rb.getString("hello"));
        System.out.println(rb.getString("open"));
    }

    public static void main(String[] args) {
        getMyLocale();
        buildOwn();
        playWithLocales();
    }
}
