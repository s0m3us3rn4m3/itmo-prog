package com.example.l9.client.commands;

import com.example.l9.common.request.Request;

public interface Command {
    public Request makeRequest(String[] args);
}
