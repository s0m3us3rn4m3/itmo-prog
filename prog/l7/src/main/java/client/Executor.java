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
import client.commands.LoginCommand;
import client.commands.PrintDescendingCommand;
import client.commands.RegisterCommand;
import client.commands.RemoveGreaterCommand;
import client.commands.RemoveKeyCommand;
import client.commands.ReplaceIfLoweCommand;
import client.commands.ShowCommand;
import client.commands.UpdateCommand;
import common.objects.Credentials;
import common.request.Request;
import common.response.Response;

public class Executor {
    public Scanner in;

    private Map<String, Command> authorizedCommands;
    private Map<String, Command> unauthorizedCommands;
    private int port;
    private Credentials savedCreds = null;

    private Executor() {
        authorizedCommands = new HashMap<String, Command>();
        authorizedCommands.put("help", new HelpCommand());
        authorizedCommands.put("info", new InfoCommand());
        authorizedCommands.put("show", new ShowCommand());
        authorizedCommands.put("insert", new InsertCommand());
        authorizedCommands.put("update", new UpdateCommand());
        authorizedCommands.put("remove_key", new RemoveKeyCommand());
        authorizedCommands.put("clear", new ClearCommand());
        authorizedCommands.put("execute_script", new ExecuteScriptCommand());
        authorizedCommands.put("remove_greater", new RemoveGreaterCommand());
        authorizedCommands.put("history", new HistoryCommand());
        authorizedCommands.put("replace_if_lowe", new ReplaceIfLoweCommand());
        authorizedCommands.put("count_less_than_genre", new CountLessThanGenreCommand());
        authorizedCommands.put("filter_less_than_usa_box_office", new FilterLessThanUsaBoxOfficeCommand());
        authorizedCommands.put("print_descending", new PrintDescendingCommand());

        unauthorizedCommands = new HashMap<String, Command>();
        unauthorizedCommands.put("login", new LoginCommand());
        unauthorizedCommands.put("register", new RegisterCommand());
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
            if (savedCreds == null && unauthorizedCommands.containsKey(cmd)) {
                Request req = unauthorizedCommands.get(cmd).makeRequest(args);
                if (req != null) {
                    Response res = sendRequest(req);
                    if (res != null) {
                        printResponse(res);
                        if (res.getSuccess() == true) {
                            savedCreds = req.getCredentials();
                        }
                    }    
                }
            } else if (savedCreds == null) {
                System.out.println("Войдите (login) или зарегистрируйтесь (register)");
            } else if (authorizedCommands.containsKey(cmd)) {
                Request req = authorizedCommands.get(cmd).makeRequest(args);
                if (req != null) {
                    req.setCredentials(savedCreds);
                    Response res = sendRequest(req);
                    if (res != null) {
                        printResponse(res);
                    }    
                }
            } else {
                System.out.println("Введена некорректная команда. Для просмотра списка команд ввеедите help");
            }
            System.out.print("> ");
            System.out.flush();
        
        }
    }

    private void printResponse(Response res) {
        String msg = res.getMessage();
        System.out.print(msg);
        if (!msg.endsWith("\n")) {
            System.out.print('\n');
        }
    }


    private Response sendRequest(Request req) {
        if (req == null) {
            return null;
        }
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
