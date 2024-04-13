package server.objects.commands;

import common.request.Request;
import server.objects.Command;
import server.objects.State;

/**
 * show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении
 */
public class ShowCommand extends Command {
    String res = "";

    public ShowCommand(State state) {
        super(state);
    }

    @Override
    public String getDescription() {
        return "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }

    @Override
    public String exec(String[] args) {
        addToHistory(args[0]);
        res = "";
        state.getCollection().values().stream().sorted((m1, m2) -> m1.getName().compareTo(m2.getName())).forEach(movie -> res += movie.toString() + '\n');
        return res;
    }

    @Override
    public String exec(Request req) {
        String[] args = {req.getCommand()};
        return exec(args);
    }
}
