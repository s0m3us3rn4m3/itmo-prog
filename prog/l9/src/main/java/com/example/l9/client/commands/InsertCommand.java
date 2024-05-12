package com.example.l9.client.commands;

import java.util.Scanner;

import com.example.l9.common.collection.Movie;
import com.example.l9.common.request.InsertRequest;
import com.example.l9.common.request.Request;

public class InsertCommand implements Command {
    @Override
    public Request makeRequest(String[] args) {
        if (args.length < 2) {
            System.out.println("usage: insert {key}");
            return null;
        }
        Movie movie = Movie.readFromScanner(new Scanner(System.in), true);
        return new InsertRequest(args[1], movie);
    }
}
