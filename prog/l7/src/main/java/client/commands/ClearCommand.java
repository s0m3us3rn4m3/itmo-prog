package client.commands;

import common.request.ClearRequest;
import common.request.Request;

public class ClearCommand implements Command {
    @Override
    public Request makeRequest(String[] args) {
        return new ClearRequest();
    }
}
