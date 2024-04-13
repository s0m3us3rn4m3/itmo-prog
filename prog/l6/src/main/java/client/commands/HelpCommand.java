package client.commands;

import common.request.HelpRequest;
import common.request.Request;

public class HelpCommand implements Command {
    @Override
    public Request makeRequest(String[] args) {
        return new HelpRequest();
    }
}
