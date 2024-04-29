package server.objects.commands;

import common.request.Request;
import common.response.Response;
import server.objects.Command;
import server.objects.State;

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
