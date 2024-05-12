package com.example.l9.client.commands;

import java.util.Scanner;

import com.example.l9.common.collection.Credentials;
import com.example.l9.common.request.RegisterRequest;
import com.example.l9.common.request.Request;

public class RegisterCommand implements Command {
    @Override
    public Request makeRequest(String[] args) {
        Credentials c = Credentials.readFromScanner(new Scanner(System.in), true);
        return new RegisterRequest(c);
    }
}
