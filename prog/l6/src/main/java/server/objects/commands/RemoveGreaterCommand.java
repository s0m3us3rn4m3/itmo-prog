package server.objects.commands;

import java.util.Map;
import java.util.TreeMap;

import common.objects.Movie;
import common.request.RemoveGreaterRequest;
import common.request.Request;
import server.objects.Command;
import server.objects.State;

/**
 * remove_greater {element} : удалить из коллекции все элементы, превышающие заданный
 */
public class RemoveGreaterCommand extends Command {
    public RemoveGreaterCommand(State state) {
        super(state);
    }

    @Override
    public String getDescription() {
        return "remove_greater {element} : удалить из коллекции все элементы, превышающие заданный";
    }

    private void filterCollection(Movie m) {
        Map<String, Movie> filtered = new TreeMap<String, Movie>();
        state.getCollection().entrySet().stream().filter(entry -> entry.getValue().compareTo(m) <= 0).forEach(entry -> filtered.put(entry.getKey(), entry.getValue()));
        state.setCollection(filtered);
    }

    @Override
    public String exec(String[] args) {
        addToHistory(args[0]);
        Movie m = Movie.readFromScanner(state.getInput(), false);
        // state.getCollection().entrySet().removeIf(entry -> entry.getValue().compareTo(m) > 0);
        filterCollection(m);
        return "ok";
    }

    @Override
    public String exec(Request req) {
        RemoveGreaterRequest r = (RemoveGreaterRequest)req;
        addToHistory(r.getCommand());
        filterCollection(r.getMovie());
        return "ok";
    }
}
