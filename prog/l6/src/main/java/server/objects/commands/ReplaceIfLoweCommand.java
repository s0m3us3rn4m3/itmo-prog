package server.objects.commands;

import java.util.Map;
import java.util.TreeMap;

import common.objects.Movie;
import common.request.ReplaceIfLoweRequest;
import common.request.Request;
import server.objects.Command;
import server.objects.State;

/**
 * replace_if_lowe key {element} : заменить значение по ключу, если новое значение меньше старого
 */
public class ReplaceIfLoweCommand extends Command {
    public ReplaceIfLoweCommand(State state) {
        super(state);
    }

    @Override
    public String getDescription() {
        return "replace_if_lowe key {element} : заменить значение по ключу, если новое значение меньше старого";
    }

    private void replaceIfLowe(String key, Movie newM) {
        Movie oldM = state.getCollection().get(key);
        if (oldM.compareTo(newM) > 0) {
            Map<String, Movie> newCollection = new TreeMap<>();
            state.getCollection().entrySet().stream().forEach(entry -> {
                if (entry.getKey() == key) {
                    newCollection.put(key, newM);
                } else {
                    newCollection.put(entry.getKey(), entry.getValue());
                }
            });
            state.setCollection(newCollection);
        }
    }

    @Override
    public String exec(String[] args) {
        addToHistory(args[0]);
        if (args.length < 2) {
            return error();
        }
        String key = args[1];
        if (!state.getCollection().containsKey(key)) {
            return "Ключ не найден";
        }
        Movie newM = Movie.readFromScanner(state.getInput(), false);
        replaceIfLowe(key, newM);
        return "ok";
    }

    @Override
    public String exec(Request req) {
        ReplaceIfLoweRequest r = (ReplaceIfLoweRequest)req;
        addToHistory(r.getCommand());
        if (!state.getCollection().containsKey(r.getKey())) {
            return "Ключ не найден";
        }
        Movie m = r.getMovie();
        m.setId(Movie.genId());
        m.setCreationDate(Movie.genCreationDate());
        replaceIfLowe(r.getKey(), m);
        return "ok";
    }
}
