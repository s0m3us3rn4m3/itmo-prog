package com.example.l9.common.request;

import com.example.l9.common.collection.Movie;

public class InsertRequest extends Request {
    private String key;
    private Movie movie;

    public InsertRequest(String k, Movie m) {
        command = "insert";
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
