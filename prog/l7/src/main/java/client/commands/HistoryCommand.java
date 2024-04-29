package client.commands;

import common.request.HistoryRequest;
import common.request.Request;

public class HistoryCommand implements Command {
    @Override
    public Request makeRequest(String[] args) {
        return new HistoryRequest();
    }
}
