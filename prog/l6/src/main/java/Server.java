package main.java;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import server.objects.Executor;

public class Server {
    public static void main(String[] args) {
        int port;
        String fileToSave;
        try {
            if (args.length < 2) {
                throw new IllegalArgumentException();
            }
            port = Integer.parseInt(args[0]);
            fileToSave = args[1];
        } catch (Exception e) {
            System.out.println("usage: ./server {port} {file_to_save}");
            return;
        }

        ServerSocket server = null;
        try {
            try {
                server = new ServerSocket(port);
                Executor e = new Executor(null, fileToSave);
                Runtime.getRuntime().addShutdownHook(new Thread() {
                    @Override
                    public void run() {
                        e.saveToFile();
                    }
                });
                e.serve(server);
            } catch (IOException e) {
                System.out.printf("failed open socket: %s\n", e.toString());
            } catch (IllegalArgumentException e) {
                System.out.printf("invalid port: %s\n", e.toString());
            } finally {
                if (server != null) {
                    server.close();
                }
            }
        } catch (Exception e) {
            System.out.printf("error: %s\n", e.toString());
        }
    }
}