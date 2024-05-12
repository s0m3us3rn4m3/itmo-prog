package com.example.l9.server.commands;

import com.example.l9.common.collection.Movie;
import com.example.l9.common.request.ReplaceIfLoweRequest;
import com.example.l9.common.request.Request;
import com.example.l9.common.response.Response;
import com.example.l9.server.Utils;
import com.example.l9.server.State;

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

    private Response replaceIfLowe(String username, String key, Movie newM) {
        state.getLock().writeLock().lock();
        try {
            if (!Utils.isOwner(state.getConn(), username, key)) {
                return new Response("Пользователь не является владельцем данной записи", false);
            }
            Movie oldM = state.getCollection().get(key);
            if (!(oldM.compareTo(newM) > 0)) {
                return success();
            }
            if (!Utils.deleteMovieFromDBByKey(state.getConn(), key)) {
                return new Response("Ошибка", false);
            }
            Long movieID = Utils.insertMovieToDB(state.getConn(), username, key, newM);
            if (movieID == null) {
                return new Response("Ошибка", false);
            }
            newM.setId(movieID);
            newM.setOwner(username);
            newM.setKey(key);
            state.getCollection().replace(key, newM);
            return success();
        } finally {
            state.getLock().writeLock().unlock();
        }
    }

    @Override
    public Response exec(String[] args) {
        addToHistory(args[0]);
        if (args.length < 2) {
            return error();
        }
        String key = args[1];
        if (!state.getCollection().containsKey(key)) {
            return new Response("Ключ не найден", false);
        }
        Movie newM = Movie.readFromScanner(state.getInput(), false);
        return replaceIfLowe(state.getUsername(), key, newM);
    }

    @Override
    public Response exec(Request req) {
        ReplaceIfLoweRequest r = (ReplaceIfLoweRequest)req;
        addToHistory(r.getCommand());
        if (!state.getCollection().containsKey(r.getKey())) {
            return new Response("Ключ не найден", false);
        }
        Movie m = r.getMovie();
        m.setCreationDate(Movie.genCreationDate());
        return replaceIfLowe(r.getCredentials().getLogin(), r.getKey(), m);
    }
}
