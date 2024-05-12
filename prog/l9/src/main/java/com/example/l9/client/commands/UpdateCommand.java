package com.example.l9.client.commands;

import java.util.Scanner;

import com.example.l9.common.collection.Movie;
import com.example.l9.common.request.Request;
import com.example.l9.common.request.UpdateRequest;

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
