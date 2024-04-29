package client.commands;

import common.request.Request;
import common.request.ShowRequest;

public class ShowCommand implements Command {
    @Override
    public Request makeRequest(String[] args) {
        return new ShowRequest();
    }
}
