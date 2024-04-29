package client.commands;

import java.util.Scanner;

import common.objects.Credentials;
import common.request.LoginRequest;
import common.request.Request;

public class LoginCommand implements Command {
    @Override
    public Request makeRequest(String[] args) {
        Credentials c = Credentials.readFromScanner(new Scanner(System.in), true);
        return new LoginRequest(c);
    }
}
