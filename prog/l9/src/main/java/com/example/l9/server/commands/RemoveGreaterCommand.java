package com.example.l9.server.commands;

import com.example.l9.common.collection.Movie;
import com.example.l9.common.request.RemoveGreaterRequest;
import com.example.l9.common.request.Request;
import com.example.l9.common.response.Response;
import com.example.l9.server.Utils;
import com.example.l9.server.State;

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

    private Response filterCollection(String username, Movie m) {
        state.getLock().writeLock().lock();
        try {
            state.getCollection().entrySet().stream().filter(entry -> entry.getValue().compareTo(m) > 0).forEach(entry -> {
                Utils.deleteMovieFromDBByID(state.getConn(), username, m.getId());
            });
            state.getCollection().entrySet().removeIf(entry -> entry.getValue().compareTo(m) > 0);
            return success();
        } finally {
            state.getLock().writeLock().unlock();
        }
    }

    @Override
    public Response exec(String[] args) {
        addToHistory(args[0]);
        Movie m = Movie.readFromScanner(state.getInput(), false);
        return filterCollection(state.getUsername(), m);
    }

    @Override
    public Response exec(Request req) {
        RemoveGreaterRequest r = (RemoveGreaterRequest)req;
        addToHistory(r.getCommand());
        return filterCollection(r.getCredentials().getLogin(), r.getMovie());
    }
}
