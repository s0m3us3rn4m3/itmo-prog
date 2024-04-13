package client.commands;

import common.request.InfoRequest;
import common.request.Request;

public class InfoCommand implements Command {
    @Override
    public Request makeRequest(String[] args) {
        return new InfoRequest();
    }
}
