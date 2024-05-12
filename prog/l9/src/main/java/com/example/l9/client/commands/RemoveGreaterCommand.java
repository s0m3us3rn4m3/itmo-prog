package com.example.l9.client.commands;

import java.util.Scanner;

import com.example.l9.common.collection.Movie;
import com.example.l9.common.request.RemoveGreaterRequest;
import com.example.l9.common.request.Request;

public class RemoveGreaterCommand implements Command {
    @Override
    public Request makeRequest(String[] args) {
        Movie movie = Movie.readFromScanner(new Scanner(System.in), true);
        return new RemoveGreaterRequest(movie);
    }
}
