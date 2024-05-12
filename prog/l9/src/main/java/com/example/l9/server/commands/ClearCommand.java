package com.example.l9.server.commands;

import com.example.l9.common.request.Request;
import com.example.l9.common.response.Response;
import com.example.l9.server.Utils;
import com.example.l9.server.State;

/**
 * clear : очистить коллекцию
 */
public class ClearCommand extends Command {
    public ClearCommand(State state) {
        super(state);
    }

    @Override
    public String getDescription() {
        return "clear : очистить коллекцию";
    }

    private Response clear(String username) {
        state.getLock().writeLock().lock();
        try {
            if (!Utils.deleteAllMoviesByUsername(state.getConn(), username)) {
                return new Response("Ошибка", false);
            }
            try {
                state.setCollection(Utils.getMoviesFromDB(state.getConn()));
            } catch (Exception e) {
                return new Response("Ошибка", false);
            }
            return success();
        } finally {
            state.getLock().writeLock().unlock();
        }
    }
    @Override
    public Response exec(String[] args) {
        addToHistory(args[0]);
        return clear(state.getUsername());
    }

    @Override
    public Response exec(Request req) {
        addToHistory(req.getCommand());
        return clear(req.getCredentials().getLogin());
    }
}
