package common.request;

import java.io.Serializable;

import common.objects.Credentials;

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
