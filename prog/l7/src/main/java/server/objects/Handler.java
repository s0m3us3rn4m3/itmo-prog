package server.objects;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import common.request.Request;
import common.response.Response;

public class Handler implements Runnable {
    private Socket sock = null;
    private Executor executor = null;

    Handler(Socket sock, Executor exec) {
        this.sock = sock;
        this.executor = exec;
    }

    private void handle() throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(sock.getInputStream());
        Request req = (Request)in.readObject();
        System.out.printf("read req with command: %s\n", req.getCommand());
        Response res = executor.run(req);
        ObjectOutputStream out = new ObjectOutputStream(sock.getOutputStream());
        out.writeObject(res);
        out.flush();
    }

    @Override
    public void run() {
        try {
            try {
                handle();
            } catch (IOException e) {
                System.out.printf("i/o error: %s\n", e);
            } catch (ClassNotFoundException e) {
                System.out.printf("error reading request: %s\n", e);
            } finally {
                if (sock != null) {
                    sock.close();
                }
            }
        } catch (IOException e) {
            System.out.printf("error: %s\n", e);
        }
    }

}
