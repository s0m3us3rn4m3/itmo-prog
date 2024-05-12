package com.example.l9.server.commands;

import com.example.l9.common.collection.Movie;
import com.example.l9.common.request.Request;
import com.example.l9.common.request.UpdateRequest;
import com.example.l9.common.response.Response;
import com.example.l9.server.Utils;
import com.example.l9.server.State;

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

    public Response updateMovie(String username, String key, Movie m) {
        state.getLock().writeLock().lock();
        try {
            if (!Utils.isOwner(state.getConn(), username, key)) {
                return new Response("Пользователь не является владельцем данной записи", false);
            }
            if (!m.getKey().equals(key) && state.getCollection().containsKey(m.getKey())) {
                return new Response("Фильм с таким ключом уже существует", false);
            }
            if (!Utils.deleteMovieFromDBByKey(state.getConn(), key)) {
                return new Response("Ошибка", false);
            }
            Long movieID = Utils.insertMovieToDB(state.getConn(), username, m.getKey(), m);
            if (movieID == null) {
                return new Response("Ошибка", false);
            }
            m.setId(movieID);
            m.setOwner(username);
            m.setKey(m.getKey());
            state.getCollection().remove(key);
            state.getCollection().put(m.getKey(), m);
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
            return new Response("Нет такого ключа", false);
        }
        Movie movie = Movie.readFromScanner(state.getInput(), false);
        return updateMovie(state.getUsername(), key, movie);
    }

    @Override
    public Response exec(Request req) {
        UpdateRequest r = (UpdateRequest)req;
        addToHistory(r.getCommand());
        if (!state.getCollection().containsKey(r.getKey())) {
            return new Response("Нет такого ключа", false);
        }
        Movie m = r.getMovie();
        m.setCreationDate(Movie.genCreationDate());
        return updateMovie(r.getCredentials().getLogin(), r.getKey(), m);
    }
}
