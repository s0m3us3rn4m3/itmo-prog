package client.commands;

import common.request.PrintDescendingRequest;
import common.request.Request;

public class PrintDescendingCommand implements Command {
    @Override
    public Request makeRequest(String[] args) {
        return new PrintDescendingRequest();
    }
}
