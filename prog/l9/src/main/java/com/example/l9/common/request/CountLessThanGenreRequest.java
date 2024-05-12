package com.example.l9.common.request;

import com.example.l9.common.collection.MovieGenre;

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
