package server.objects;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import common.request.Request;
import common.response.Response;
import server.interfaces.CommandInterface;
import server.objects.commands.ClearCommand;
import server.objects.commands.CountLessThanGenreCommand;
import server.objects.commands.ExecuteScriptCommand;
import server.objects.commands.ExitCommand;
import server.objects.commands.FilterLessThanUsaBoxOffice;
import server.objects.commands.HistoryCommand;
import server.objects.commands.InfoCommand;
import server.objects.commands.InsertCommand;
import server.objects.commands.PrintDescending;
import server.objects.commands.RemoveGreaterCommand;
import server.objects.commands.RemoveKeyCommand;
import server.objects.commands.ReplaceIfLoweCommand;
import server.objects.commands.SaveCommand;
import server.objects.commands.ShowCommand;
import server.objects.commands.UpdateCommand;

/**
 * Класс, который хранит коллекцию и выполняет команды
 */
public class Executor {
    private Map<String, CommandInterface> commandMap;
    private State state;

    private void initMethodMap() {
        this.commandMap = new HashMap<String, CommandInterface>();
        commandMap.put("help", new Command(state) {
            public String getDescription() {
                return "help : вывести справку по доступным командам";
            }

            @Override
            public String exec(String[] args) {
                addToHistory(args[0]);
                String msg = "";
                for (CommandInterface command : commandMap.values()) {
                    msg += command.getDescription() + '\n';
                }
                return msg;
            }
            
            @Override
            public String exec(Request req) {
                String[] args = {req.getCommand()};
                return exec(args);
            }
        });
        commandMap.put("info", new InfoCommand(state));
        commandMap.put("show", new ShowCommand(state));
        commandMap.put("insert", new InsertCommand(state));
        commandMap.put("update", new UpdateCommand(state));
        commandMap.put("remove_key", new RemoveKeyCommand(state));
        commandMap.put("clear", new ClearCommand(state));
        commandMap.put("save", new SaveCommand(state));
        commandMap.put("execute_script", new ExecuteScriptCommand(state));
        commandMap.put("exit", new ExitCommand(state));
        commandMap.put("remove_greater", new RemoveGreaterCommand(state));
        commandMap.put("history", new HistoryCommand(state));
        commandMap.put("replace_if_lowe", new ReplaceIfLoweCommand(state));
        commandMap.put("count_less_than_genre", new CountLessThanGenreCommand(state));
        commandMap.put("filter_less_than_usa_box_office", new FilterLessThanUsaBoxOffice(state));
        commandMap.put("print_descending", new PrintDescending(state));
    }


    public Executor(Scanner input, String fileToSave) {
        state = new State();
        state.setFileToSave(fileToSave);
        state.setInput(input);
        initMethodMap();

        try {
            state.parseFileToSave();
        } catch (Exception e) {
            System.out.printf("Не удалось загрузить файл: %s\n", e);
        }
    }

    public Executor(Scanner input, State old_state) {
        state = old_state;
        state.setInput(input);
        initMethodMap();
    }

    public void saveToFile() {
        try {
            state.saveToFile();
        } catch (Exception e) {
            System.out.printf("Не удалось сохранить: %s\n", e);
        }
    }

    /**
     * Считывает и выполняет команды, пока не придет команда exit или не закроется
     * поток
     * 
     */
    public String run() {
        String res = "";
        while (state.getInput().hasNextLine()) {
            String line = state.getInput().nextLine();
            String[] args = line.split(" ");
            String cmd = args[0].toLowerCase();
            if (commandMap.containsKey(cmd)) {
                res += commandMap.get(cmd).exec(args);
                if (cmd == "exit") {
                    break;
                }
            } else {
                res += "Введена некорректная команда. Для просмотра списка команд ввеедите help";
            }
            res += '\n';
        }
        return res;
    }

    public Response run(Request req) {
        String res = null;
        if (commandMap.containsKey(req.getCommand())) {
            res = commandMap.get(req.getCommand()).exec(req);
        } else {
            res = "Введена некорректная команда. Для просмотра списка команд ввеедите help";
        }
        return new Response(res);
    }

    public void handle(Socket sock) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(sock.getInputStream());
        Request req = (Request)in.readObject();
        System.out.printf("read req with command: %s\n", req.getCommand());
        Response res = run(req);
        ObjectOutputStream out = new ObjectOutputStream(sock.getOutputStream());
        out.writeObject(res);
        out.flush();
    }

    public void serve(ServerSocket srv) throws IOException {
        Socket sock = null;
        while (true) {
            try {
                sock = srv.accept();
                System.out.printf("get connection from %s\n", sock.getInetAddress());
                handle(sock);
            } catch (IOException e) {
                System.out.printf("i/o error: %s\n", e);
            } catch (ClassNotFoundException e) {
                System.out.printf("error reading request: %s\n", e);
            } finally {
                sock.close();
            }
        }
    }
}
