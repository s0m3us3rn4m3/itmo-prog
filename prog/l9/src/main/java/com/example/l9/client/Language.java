package com.example.l9.client;

import java.util.Locale;

public enum Language {
    RU("Русский", Locale.forLanguageTag("ru-RU")),
    FI("Suomi", Locale.forLanguageTag("fi-FI")),
    HR("Hrvatski", Locale.forLanguageTag("hr-HR")),
    EN_NZ("English (NZ)", Locale.forLanguageTag("en-NZ"));

    private final String lang;
    private final Locale locale;

    Language(String s, Locale l) {
        lang = s;
        locale = l;
    }

    public Locale getLocale() {
        return locale;
    }

    @Override
    public String toString() {
        return lang;
    }

    public static Language getLanguage(String s) {
        for (Language l : Language.values()) {
            if (l.toString().equals(s)) {
                return l;
            }
        }
        throw new IllegalArgumentException();
    }
}
