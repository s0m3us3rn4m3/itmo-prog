package com.example.l9.client;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;

public class LanguageManager {
    private ResourceBundle resourceBundle;

    public LanguageManager(Language l) {
        switchLanguage(l);
    }

    public void switchLanguage(Language l) {
        resourceBundle = ResourceBundle.getBundle("com.example.l9/locales/", l.getLocale());
    }

    public String getDate(LocalDate date) {
        if (date == null) return "null";
        DateTimeFormatter formatter = DateTimeFormatter
                .ofLocalizedDate(FormatStyle.FULL)
                .withLocale(resourceBundle.getLocale());
        return date.format(formatter);
    }

    public String getDateTime(LocalDateTime date) {
        if (date == null) return "null";
        DateTimeFormatter formatter = DateTimeFormatter
                .ofLocalizedDate(FormatStyle.FULL)
                .withLocale(resourceBundle.getLocale());
        return date.format(formatter);
    }

    public String getStringByKey(String key) {
        return resourceBundle.getString(key);
    }

}
