package com.example.l9.client.commands;

import com.example.l9.common.request.ClearRequest;
import com.example.l9.common.request.Request;

public class ClearCommand implements Command {
    @Override
    public Request makeRequest(String[] args) {
        return new ClearRequest();
    }
}
