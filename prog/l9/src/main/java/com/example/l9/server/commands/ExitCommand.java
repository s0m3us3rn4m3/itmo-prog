package com.example.l9.server.commands;

import com.example.l9.common.request.Request;
import com.example.l9.common.response.Response;
import com.example.l9.server.State;

/**
 * exit : завершить программу
 */
public class ExitCommand extends Command {
    public ExitCommand(State state) {
        super(state);
    }

    @Override
    public String getDescription() {
        return "exit : завершить программу";
    }

    @Override
    public Response exec(String[] args) {
        addToHistory(args[0]);
        return success();
    }

    @Override
    public Response exec(Request req) {
        addToHistory(req.getCommand());
        return success();
    }
}
