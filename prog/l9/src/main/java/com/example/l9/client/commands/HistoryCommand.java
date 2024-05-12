package com.example.l9.client.commands;

import com.example.l9.common.request.HistoryRequest;
import com.example.l9.common.request.Request;

public class HistoryCommand implements Command {
    @Override
    public Request makeRequest(String[] args) {
        return new HistoryRequest();
    }
}
