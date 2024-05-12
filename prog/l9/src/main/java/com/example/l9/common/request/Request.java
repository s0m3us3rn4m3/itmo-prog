package com.example.l9.common.request;

import java.io.Serializable;

import com.example.l9.common.collection.Credentials;

public class Request implements Serializable {
    protected String command;
    protected Credentials credentials;

    public String getCommand() {
        return command;
    }

    public void setCredentials(Credentials c) {
        credentials = c;
    }

    public Credentials getCredentials() {
        return credentials;
    }
}
