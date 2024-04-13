package client.commands;

import java.util.Scanner;

import common.objects.Movie;
import common.request.Request;
import common.request.UpdateRequest;

public class UpdateCommand implements Command {
    @Override
    public Request makeRequest(String[] args) {
        if (args.length < 2) {
            System.out.println("usage: update {key}");
            return null;
        }
        Movie movie = Movie.readFromScanner(new Scanner(System.in), true);
        return new UpdateRequest(args[1], movie);
    }
}
