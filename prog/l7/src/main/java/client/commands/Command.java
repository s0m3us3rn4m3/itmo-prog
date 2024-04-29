package client.commands;

import common.request.Request;

public interface Command {
    public Request makeRequest(String[] args);
}
