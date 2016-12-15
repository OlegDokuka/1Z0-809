package ua.rainbow.exam.l10n;


import java.util.ListResourceBundle;

public class Zoo_ua extends ListResourceBundle {

    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"hello", "Вітаємо!"},
                {"open", "Зоопарк відкрито!"}
        };
    }
}
