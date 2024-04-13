package server.objects.commands;

import common.request.CountLessThanGenreRequest;
import common.request.Request;
import server.enums.MovieGenre;
import server.objects.Command;
import server.objects.State;

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

    @Override
    public String exec(String[] args) {
        addToHistory(args[0]);
        if (args.length < 2) {
            return error();
        }
        String gStr = args[1];
        try {
            MovieGenre.valueOf(gStr);
        } catch (Exception e) {
            return "Нет такого жанра";
        }
        long count = state.getCollection().values().stream().filter(movie -> movie.getMovieGenre().toString().compareTo(gStr) < 0).count();
        return String.format("%d\n", count);
    }

    @Override
    public String exec(Request req) {
        CountLessThanGenreRequest r = (CountLessThanGenreRequest)req;
        String[] args = {r.getCommand(), r.getGenre().toString()};
        return exec(args);
    }
}
