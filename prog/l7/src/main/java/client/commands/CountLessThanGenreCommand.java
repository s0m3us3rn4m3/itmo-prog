package client.commands;

import common.request.CountLessThanGenreRequest;
import common.request.Request;
import server.enums.MovieGenre;

public class CountLessThanGenreCommand implements Command {
    @Override
    public Request makeRequest(String[] args) {
        if (args.length < 2) {
            System.out.println("usage: count_less_than_genre {genre}");
            return null;
        }
        MovieGenre g;
        try {
            g = MovieGenre.valueOf(args[1]);
        } catch (Exception e) {
            System.out.println("Нет такого жанра");
            return null;
        }
        return new CountLessThanGenreRequest(g);
    }
}
