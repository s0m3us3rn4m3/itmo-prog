package client.commands;

import java.util.Scanner;

import common.objects.Movie;
import common.request.Request;
import common.request.ReplaceIfLoweRequest;

public class ReplaceIfLoweCommand implements Command {
    @Override
    public Request makeRequest(String[] args) {
        if (args.length < 2) {
            System.out.println("usage: replace_if_lowe {key}");
            return null;
        }
        Movie movie = Movie.readFromScanner(new Scanner(System.in), true);
        return new ReplaceIfLoweRequest(args[1], movie);
    }
}
