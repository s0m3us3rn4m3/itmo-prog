package main.java;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.util.Scanner;

import server.objects.Executor;
import server.objects.SocketServer;

public class Server {
    public static void main(String[] args) {
        int port;
        try {
            if (args.length < 1) {
                throw new IllegalArgumentException();
            }
            port = Integer.parseInt(args[0]);
        } catch (Exception e) {
            System.out.println("usage: ./server {port}");
            return;
        }

        try {
            try {
                Executor e = new Executor(null);
                SocketServer srv = new SocketServer(4);
                srv.serve(port, e);
            } catch (IOException e) {
                System.out.printf("failed open socket: %s\n", e.toString());
            } catch (IllegalArgumentException e) {
                System.out.printf("invalid port: %s\n", e.toString());
            }
        } catch (Exception e) {
            System.out.printf("error: %s\n", e.toString());
        }
    }
}