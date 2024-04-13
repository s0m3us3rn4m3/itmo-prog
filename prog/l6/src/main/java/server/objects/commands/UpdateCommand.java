package server.objects.commands;

import common.objects.Movie;
import common.request.Request;
import common.request.UpdateRequest;
import server.objects.Command;
import server.objects.State;

/**
 * update id {element} : обновить значение элемента коллекции, id которого равен заданному
 */
public class UpdateCommand extends Command {
    public UpdateCommand(State state) {
        super(state);
    }

    @Override
    public String getDescription() {
        return "update key {element} : обновить значение элемента коллекции, key которого равен заданному";
    }

    @Override
    public String exec(String[] args) {
        addToHistory(args[0]);
        if (args.length < 2) {
            return error();
        }
        String key = args[1];
        if (!state.getCollection().containsKey(key)) {
            return "Нет такого ключа";
        } else {
            Movie movie = Movie.readFromScanner(state.getInput(), false);
            state.getCollection().replace(key, movie);
        }
        return "ok";
    }

    @Override
    public String exec(Request req) {
        UpdateRequest r = (UpdateRequest)req;
        addToHistory(r.getCommand());
        if (!state.getCollection().containsKey(r.getKey())) {
            return "Нет такого ключа";
        } else {
            Movie m = r.getMovie();
            m.setId(Movie.genId());
            m.setCreationDate(Movie.genCreationDate());
            state.getCollection().replace(r.getKey(), m);
            return "ok";
        }
    }
}
