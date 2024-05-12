package com.example.l9.common.response;

import com.example.l9.common.collection.Movie;

import java.util.List;

public class ShowResponse extends Response {
    private List<Movie> movies;

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public List<Movie> getMovies() {
        return movies;
    }
}
