package server.objects.commands;


import common.request.Request;
import server.objects.Command;
import server.objects.State;

/**
 * info : вывести в стандартный поток вывода информацию о коллекции
 */
public class InfoCommand extends Command {
    public InfoCommand(State state) {
        super(state);
    }

    @Override
    public String getDescription() {
        return "info : вывести в стандартный поток вывода информацию о коллекции";
    }

    @Override
    public String exec(String[] args) {
        addToHistory(args[0]);
        return "Тип: TreeMap<String, Movie>\n" + String.format("Количество элементов: %d\n", state.getCollection().size());
    }

    @Override
    public String exec(Request req) {
        String[] args = {req.getCommand()};
        return exec(args);
    }
}
