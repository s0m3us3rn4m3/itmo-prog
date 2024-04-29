package common.request;

import common.objects.Movie;

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
