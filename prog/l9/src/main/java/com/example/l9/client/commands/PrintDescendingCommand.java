package com.example.l9.client.commands;

import com.example.l9.common.request.PrintDescendingRequest;
import com.example.l9.common.request.Request;

public class PrintDescendingCommand implements Command {
    @Override
    public Request makeRequest(String[] args) {
        return new PrintDescendingRequest();
    }
}
