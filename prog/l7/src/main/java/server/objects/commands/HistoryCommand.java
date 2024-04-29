package server.objects.commands;

import common.request.Request;
import common.response.Response;
import server.objects.Command;
import server.objects.State;

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
