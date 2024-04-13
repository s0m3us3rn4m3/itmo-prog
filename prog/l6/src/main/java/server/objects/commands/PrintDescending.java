package server.objects.commands;

import common.request.Request;
import server.objects.Command;
import server.objects.State;

/**
 * print_descending : вывести элементы коллекции в порядке убывания
 */
public class PrintDescending extends Command {
    public PrintDescending(State state) {
        super(state);
    }

    @Override
    public String getDescription() {
        return "print_descending : вывести элементы коллекции в порядке убывания";
    }

    String res = "";
    @Override
    public String exec(String[] args) {
        res = "";
        addToHistory(args[0]);
        state.getCollection().values().stream().sorted((m1, m2) -> -m1.compareTo(m2)).forEach(movie -> res += movie.toString() + '\n');;
        return res;
    }

    @Override
    public String exec(Request req) {
        String[] args = {req.getCommand()};
        return exec(args);
    }
}
