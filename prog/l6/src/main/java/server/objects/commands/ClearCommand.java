package server.objects.commands;

import common.request.Request;
import server.objects.Command;
import server.objects.State;

/**
 * clear : очистить коллекцию
 */
public class ClearCommand extends Command {
    public ClearCommand(State state) {
        super(state);
    }

    @Override
    public String getDescription() {
        return "clear : очистить коллекцию";
    }

    @Override
    public String exec(String[] args) {
        addToHistory(args[0]);
        state.getCollection().clear();
        return "ok";
    }

    @Override
    public String exec(Request req) {
        String[] args = {req.getCommand()};
        return exec(args);
    }
}
