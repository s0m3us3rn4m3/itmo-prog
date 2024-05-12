package com.example.l9.common.request;

import com.example.l9.common.collection.Movie;

public class RemoveGreaterRequest extends Request {
    private Movie movie;

    public RemoveGreaterRequest(Movie m) {
        command = "remove_greater";
        movie = m;
    }

    public Movie getMovie() {
        return movie;
    }
}
