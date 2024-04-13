package common.request;

import server.enums.MovieGenre;

public class CountLessThanGenreRequest extends Request {
    MovieGenre genre;

    public CountLessThanGenreRequest(MovieGenre g) {
        command = "count_less_than_genre";
        genre = g;
    }

    public MovieGenre getGenre() {
        return genre;
    }
}
