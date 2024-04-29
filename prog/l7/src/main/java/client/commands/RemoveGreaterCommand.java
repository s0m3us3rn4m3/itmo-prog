package client.commands;

import java.util.Scanner;

import common.objects.Movie;
import common.request.RemoveGreaterRequest;
import common.request.Request;

public class RemoveGreaterCommand implements Command {
    @Override
    public Request makeRequest(String[] args) {
        Movie movie = Movie.readFromScanner(new Scanner(System.in), true);
        return new RemoveGreaterRequest(movie);
    }
}
