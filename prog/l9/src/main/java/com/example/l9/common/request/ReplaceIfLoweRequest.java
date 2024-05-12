package com.example.l9.common.request;

import com.example.l9.common.collection.Movie;

public class ReplaceIfLoweRequest extends Request {
    String key;
    Movie movie;

    public ReplaceIfLoweRequest(String k, Movie m) {
        command = "replace_if_lowe";
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
