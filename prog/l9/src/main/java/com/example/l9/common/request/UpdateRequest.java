package com.example.l9.common.request;

import com.example.l9.common.collection.Movie;

public class UpdateRequest extends Request {
    private String key;
    private Movie movie;

    public UpdateRequest(String k, Movie m) {
        command = "update";
        key = k;
        movie = m;
    }

    public String getKey() {
        return key;
    }

    public Movie getMovie() {
        return movie;
    }
}
