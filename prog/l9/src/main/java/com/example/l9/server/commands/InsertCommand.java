package com.example.l9.server.commands;

import com.example.l9.common.collection.Movie;
import com.example.l9.common.request.InsertRequest;
import com.example.l9.common.request.Request;
import com.example.l9.common.response.Response;
import com.example.l9.server.Utils;
import com.example.l9.server.State;

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

    private Response insert(String username, String key, Movie m) {
        state.getLock().writeLock().lock();
        try {
            Long movieId = Utils.insertMovieToDB(state.getConn(), username, key, m);
            if (movieId != null) {
                m.setOwner(username);
                m.setId(movieId);
                m.setKey(key);
                state.getCollection().put(key, m);
                return success();
            }
            return new Response("Ошибка", false);
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
        if (state.getCollection().containsKey(key)) {
            return new Response("Такой ключ уже существует", false);
        }
        Movie movie = Movie.readFromScanner(state.getInput(), false);
        return insert(state.getUsername(), key, movie);
    }

    @Override
    public Response exec(Request req) {
        InsertRequest r = (InsertRequest)req;
        addToHistory(req.getCommand());
        if (state.getCollection().containsKey(r.getKey())) {
            return new Response("Такой ключ уже существует", false);
        }
        Movie m = r.getMovie();
        m.setCreationDate(Movie.genCreationDate());
        return insert(r.getCredentials().getLogin(), r.getKey(), m);
    }
}
