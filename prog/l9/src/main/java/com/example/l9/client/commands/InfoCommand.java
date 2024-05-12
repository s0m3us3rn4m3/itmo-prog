package com.example.l9.client.commands;

import com.example.l9.common.request.InfoRequest;
import com.example.l9.common.request.Request;

public class InfoCommand implements Command {
    @Override
    public Request makeRequest(String[] args) {
        return new InfoRequest();
    }
}
