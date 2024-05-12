package com.example.l9.client.commands;

import com.example.l9.common.request.HelpRequest;
import com.example.l9.common.request.Request;

public class HelpCommand implements Command {
    @Override
    public Request makeRequest(String[] args) {
        return new HelpRequest();
    }
}
