package common.request;

import common.objects.Movie;

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
