package com.example.l9.server.commands;

import com.example.l9.common.request.Request;
import com.example.l9.common.response.Response;

/**
 * Интерфейс для команды
 */
public interface CommandInterface {
    public String getDescription();
    public Response exec(String[] args);
    public Response exec(Request req);
}
