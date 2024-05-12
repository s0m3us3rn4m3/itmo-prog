package com.example.l9.server.commands;

import com.example.l9.common.request.CountLessThanGenreRequest;
import com.example.l9.common.request.Request;
import com.example.l9.common.response.Response;
import com.example.l9.common.collection.MovieGenre;
import com.example.l9.server.State;

/**
 * count_less_than_genre genre : вывести количество элементов, значение поля genre которых меньше заданного
 */
public class CountLessThanGenreCommand extends Command {
    public CountLessThanGenreCommand(State state) {
        super(state);
    }

    @Override
    public String getDescription() {
        return "count_less_than_genre genre : вывести количество элементов, значение поля genre которых меньше заданного";
    }

    private long countLessThanGenre(String genre) {
        state.getLock().readLock().lock();
        try {
            return state.getCollection().values().stream().filter(movie -> movie.getMovieGenre().toString().compareTo(genre) < 0).count();
        } finally {
            state.getLock().readLock().unlock();
        }
    }

    @Override
    public Response exec(String[] args) {
        addToHistory(args[0]);
        if (args.length < 2) {
            return error();
        }
        String gStr = args[1];
        try {
            MovieGenre.valueOf(gStr);
        } catch (Exception e) {
            return new Response("Нет такого жанра", false);
        }
        return new Response(String.format("%d\n", countLessThanGenre(gStr)), true);
    }

    @Override
    public Response exec(Request req) {
        CountLessThanGenreRequest r = (CountLessThanGenreRequest)req;
        String[] args = {r.getCommand(), r.getGenre().toString()};
        return exec(args);
    }
}
