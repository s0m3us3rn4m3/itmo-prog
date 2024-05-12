package com.example.l9.client.commands;

import java.util.Scanner;

import com.example.l9.common.collection.Movie;
import com.example.l9.common.request.Request;
import com.example.l9.common.request.ReplaceIfLoweRequest;

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
