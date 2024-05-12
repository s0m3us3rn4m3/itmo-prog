package com.example.l9;

import java.io.IOException;

import com.example.l9.server.Executor;
import com.example.l9.server.SocketServer;

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