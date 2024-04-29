package main.java;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

import client.Executor;

public class Client {
    public static void main(String[] args) {
        int port;
        try {
            if (args.length < 1) {
                throw new IllegalArgumentException();
            }
            port = Integer.parseInt(args[0]);
        } catch (Exception e) {
            System.out.println("usage: ./client {port}");
            return;
        }

        try {
            Executor e = new Executor(new Scanner(System.in), port);
            e.run();
        } catch (Exception e) {
            System.out.printf("error: %s\n", e);
        }
    }
}