package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import client.commands.ClearCommand;
import client.commands.Command;
import client.commands.CountLessThanGenreCommand;
import client.commands.ExecuteScriptCommand;
import client.commands.FilterLessThanUsaBoxOfficeCommand;
import client.commands.HelpCommand;
import client.commands.HistoryCommand;
import client.commands.InfoCommand;
import client.commands.InsertCommand;
import client.commands.PrintDescendingCommand;
import client.commands.RemoveGreaterCommand;
import client.commands.RemoveKeyCommand;
import client.commands.ReplaceIfLoweCommand;
import client.commands.ShowCommand;
import client.commands.UpdateCommand;
import common.request.Request;
import common.response.Response;

public class Executor {
    public Scanner in;

    private Map<String, Command> commands;
    private int port;


    private Executor() {
        commands = new HashMap<String, Command>();
        commands.put("help", new HelpCommand());
        commands.put("info", new InfoCommand());
        commands.put("show", new ShowCommand());
        commands.put("insert", new InsertCommand());
        commands.put("update", new UpdateCommand());
        commands.put("remove_key", new RemoveKeyCommand());
        commands.put("clear", new ClearCommand());
        commands.put("execute_script", new ExecuteScriptCommand());
        commands.put("remove_greater", new RemoveGreaterCommand());
        commands.put("history", new HistoryCommand());
        commands.put("replace_if_lowe", new ReplaceIfLoweCommand());
        commands.put("count_less_than_genre", new CountLessThanGenreCommand());
        commands.put("filter_less_than_usa_box_office", new FilterLessThanUsaBoxOfficeCommand());
        commands.put("print_descending", new PrintDescendingCommand());
    }

    private void ping(int port) throws Exception {
        Socket sock = new Socket("localhost", port);
        sock.close();
    }

    public Executor(Scanner in, int port) throws Exception {
        this();
        this.in = in;
        this.port = port;
        ping(port);
    }

    public void run() {
        System.out.print("> ");
        System.out.flush();
        while (in.hasNextLine()) {
            String line = in.nextLine();
            String[] args = line.split(" ");
            String cmd = args[0].toLowerCase();
            if (cmd.equals("exit")) {
                return;
            }
            if (commands.containsKey(cmd)) {
                Request req = commands.get(cmd).makeRequest(args);
                if (req != null) {
                    Response res = sendRequest(req);
                    if (res != null) {
                        System.out.print(res.message);
                        if (!res.message.endsWith("\n")) {
                            System.out.print('\n');
                        }
                    }
                }
            } else {
                System.out.println("Введена некорректная команда. Для просмотра списка команд ввеедите help");
            }
            System.out.print("> ");
            System.out.flush();
        
        }
    }

    private Response sendRequest(Request req) {
        try {
            Socket sock = null;
            try {
                sock = new Socket("localhost", port);
                ObjectOutputStream out = new ObjectOutputStream(sock.getOutputStream());
                out.writeObject(req);
                out.flush();
                ObjectInputStream in = new ObjectInputStream(sock.getInputStream());
                return (Response)in.readObject();
            } catch (IOException e) {
                System.out.printf("connection err: %s\n", e);
            } catch (ClassNotFoundException e) {
                System.out.printf("reading response err: %s\n", e);
            } finally {
                if (sock != null) {
                    sock.close();
                }
            }
        } catch (IOException e) {}
        return null;
    }
}
