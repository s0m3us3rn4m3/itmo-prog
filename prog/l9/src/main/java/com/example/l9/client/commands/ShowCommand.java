package com.example.l9.client.commands;

import com.example.l9.common.request.Request;
import com.example.l9.common.request.ShowRequest;

public class ShowCommand implements Command {
    @Override
    public Request makeRequest(String[] args) {
        return new ShowRequest();
    }
}
