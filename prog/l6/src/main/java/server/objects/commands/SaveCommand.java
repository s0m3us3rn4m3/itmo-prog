package server.objects.commands;

import common.request.Request;
import server.objects.Command;
import server.objects.State;

/**
 * save : сохранить коллекцию в файл
 */
public class SaveCommand extends Command {
    public SaveCommand(State state) {
        super(state);
    }

    @Override
    public String getDescription() {
        return "save : сохранить коллекцию в файл";
    }

    @Override
    public String exec(String[] args) {
        addToHistory(args[0]);
        try {
            state.saveToFile();
        } catch (Exception e) {
            return String.format("Не удалось сохранить: %s\n", e);
        }
        return "ok";
    }

    @Override
    public String exec(Request req) {
        String[] args = {req.getCommand()};
        return exec(args);
    }
}
