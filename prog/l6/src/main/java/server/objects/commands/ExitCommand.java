package server.objects.commands;

import common.request.Request;
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
    public String exec(String[] args) {
        addToHistory(args[0]);
        return "ok";
    }

    @Override
    public String exec(Request req) {
        addToHistory(req.getCommand());
        try {
            state.saveToFile();
        } catch (Exception e) {
            return String.format("Не удалось сохранить: %s\n", e);
        }
        return "ok";
    }
}
