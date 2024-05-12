package com.example.l9.server.commands;

import com.example.l9.common.request.Request;
import com.example.l9.common.response.Response;
import com.example.l9.server.State;

/**
 * history : вывести последние 7 команд (без их аргументов)
 */
public class HistoryCommand extends Command {
    String res = "";

    public HistoryCommand(State state) {
        super(state);
    }

    @Override
    public String getDescription() {
        return "history : вывести последние 7 команд (без их аргументов)";
    }

    @Override
    public Response exec(String[] args) {
        res = "";
        state.getHistory().stream().forEach(cmd -> res += cmd + '\n');
        addToHistory(args[0]);
        return new Response(res, true);
    }

    @Override
    public Response exec(Request req) {
        String[] args = {req.getCommand()};
        return exec(args);
    }
}
