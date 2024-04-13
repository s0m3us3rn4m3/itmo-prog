package server.objects.commands;

import common.objects.Movie;
import common.request.InsertRequest;
import common.request.Request;
import server.objects.Command;
import server.objects.State;

/**
 * insert key {element} : добавить новый элемент с заданным ключом
 */
public class InsertCommand extends Command {
    public InsertCommand(State state) {
        super(state);
    }
    
    @Override
    public String getDescription() {
        return "insert key {element} : добавить новый элемент с заданным ключом";
    }

    private void insert(String key, Movie m) {
        state.getCollection().put(key, m);
    }

    @Override
    public String exec(String[] args) {
        addToHistory(args[0]);
        if (args.length < 2) {
            return error();
        }
        String key = args[1];
        if (state.getCollection().containsKey(key)) {
            return "Такой ключ уже существует";
        }
        Movie movie = Movie.readFromScanner(state.getInput(), false);
        insert(key, movie);
        return "ok";
    }

    @Override
    public String exec(Request req) {
        InsertRequest r = (InsertRequest)req;
        addToHistory(req.getCommand());
        if (state.getCollection().containsKey(r.getKey())) {
            return "Такой ключ уже существует";
        }
        Movie m = r.getMovie();
        m.setId(Movie.genId());
        m.setCreationDate(Movie.genCreationDate());
        insert(r.getKey(), m);
        return "ok";
    }
}
