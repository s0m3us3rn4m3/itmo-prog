package com.example.l9.client;

import com.example.l9.common.collection.Credentials;

public class Session {
    private static Credentials creds;
    private static Language language = Language.RU;

    public static void setCreds(Credentials c) {
        creds = c;
    }

    public static void setLanguage(Language language) {
        Session.language = language;
    }

    public static Credentials getCreds() {
        return creds;
    }

    public static Language getLanguage() {
        return language;
    }
}
