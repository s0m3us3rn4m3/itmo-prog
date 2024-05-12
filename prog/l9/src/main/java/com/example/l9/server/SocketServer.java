package com.example.l9.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketServer {
    private ExecutorService pool = null;

    public SocketServer(int nThreads) {
        pool = Executors.newFixedThreadPool(nThreads);
    }

    public void serve(int port, Executor executor) throws Exception {
        ServerSocket srv = null;
        try {
            srv = new ServerSocket(port);
            Socket sock = null;
            System.out.printf("listening at %d\n", srv.getLocalPort());
            while (true) {
                sock = srv.accept();
                System.out.printf("get connection from %s\n", sock.getInetAddress());
                pool.execute(new Handler(sock, executor));
            }
        } finally {
            if (srv != null) {
                srv.close();
            }
        }
    }
}
