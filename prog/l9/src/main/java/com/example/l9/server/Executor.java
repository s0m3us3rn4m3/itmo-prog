package com.example.l9.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;

import com.example.l9.common.request.Request;
import com.example.l9.common.response.Response;
import com.example.l9.server.commands.*;

/**
 * Класс, который хранит коллекцию и выполняет команды
 */
public class Executor {
    private Map<String, CommandInterface> commandMap;
    private Map<String, CommandInterface> unauthorizedCommandMap;
    private State state;

    private void initMethodMap() {
        this.commandMap = new HashMap<String, CommandInterface>();
        commandMap.put("help", new Command(state) {
            public String getDescription() {
                return "help : вывести справку по доступным командам";
            }

            private String msg;

            @Override
            public Response exec(String[] args) {
                addToHistory(args[0]);
                msg = "";
                msg = "";
                commandMap.entrySet().stream()
                    .filter(entry -> !entry.getKey().equals("save"))
                    .sorted((e1, e2) -> e1.getKey().compareTo(e2.getKey()))
                    .forEach(entry -> msg += entry.getValue().getDescription() + '\n');
                return new Response(msg, true);
            }
            
            @Override
            public Response exec(Request req) {
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
        commandMap.put("execute_script", new ExecuteScriptCommand(state));
        commandMap.put("exit", new ExitCommand(state));
        commandMap.put("remove_greater", new RemoveGreaterCommand(state));
        commandMap.put("history", new HistoryCommand(state));
        commandMap.put("replace_if_lowe", new ReplaceIfLoweCommand(state));
        commandMap.put("count_less_than_genre", new CountLessThanGenreCommand(state));
        commandMap.put("filter_less_than_usa_box_office", new FilterLessThanUsaBoxOffice(state));
        commandMap.put("print_descending", new PrintDescending(state));
        
        this.unauthorizedCommandMap = new HashMap<String, CommandInterface>();
        unauthorizedCommandMap.put("login", new LoginCommand(state));
        unauthorizedCommandMap.put("register", new RegisterCommand(state));
    }

    private Connection connectToDB() throws SQLException {
//        Connection conn = DriverManager.getConnection("jdbc:postgresql://pg/studs?user=s408102&ssl=false");
        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/app?user=app&password=app&ssl=false");
        return conn;
    }

    private void initDB() throws SQLException {
        PreparedStatement s = state.getConn().prepareStatement(
            "CREATE TABLE IF NOT EXISTS users (" +
            "username VARCHAR PRIMARY KEY,"+
            "password VARCHAR NOT NULL"+
            ");"+
            "CREATE TABLE IF NOT EXISTS coordinates ("+
            "id SERIAL PRIMARY KEY,"+
            "x INTEGER NOT NULL,"+
            "y REAL NOT NULL"+
            ");"+
            "CREATE TABLE IF NOT EXISTS locations ("+
            "id SERIAL PRIMARY KEY,"+
            "x REAL NOT NULL,"+
            "y REAL NOT NULL,"+
            "z REAL NOT NULL,"+
            "name VARCHAR NOT NULL"+
            ");"+
            "CREATE TABLE IF NOT EXISTS screenwriters ("+
            "id SERIAL PRIMARY KEY,"+
            "name VARCHAR NOT NULL,"+
            "height INTEGER NOT NULL,"+
            "nationality VARCHAR NOT NULL,"+
            "location_id SERIAL REFERENCES locations(id) NOT NULL"+
            ");"+
            "CREATE TABLE IF NOT EXISTS movies ("+
            "id SERIAL PRIMARY KEY,"+
            "username VARCHAR REFERENCES users(username),"+
            "key VARCHAR NOT NULL,"+
            "name VARCHAR NOT NULL,"+
            "coords_id SERIAL REFERENCES coordinates(id) NOT NULL,"+
            "creation_date TIMESTAMP NOT NULL,"+
            "oscars_count BIGINT NOT NULL,"+
            "usa_box_office REAL NOT NULL,"+
            "genre VARCHAR NOT NULL,"+
            "mpaa_rating VARCHAR NOT NULL"+
            ");"+
            "CREATE TABLE IF NOT EXISTS screenwriter_movie ("+
            "screenwriter_id SERIAL REFERENCES screenwriters(id) ON DELETE CASCADE,"+
            "movie_id SERIAL REFERENCES movies(id),"+
            "PRIMARY KEY (screenwriter_id, movie_id)"+
            ");"
        );
        s.executeUpdate();
    }

    public Executor(Scanner input) throws Exception {
        state = new State();
        state.setInput(input);
        initMethodMap();

        Connection conn = connectToDB();
        state.setConn(conn);
        initDB();
        state.setCollection(Utils.getMoviesFromDB(conn));
    }

    public Executor(Scanner input, State old_state) {
        state = new State();
        state.setCollection(old_state.getCollection());
        state.setHistory(old_state.getHistory());
        state.setLock(old_state.getLock());
        state.setHistoryLock(old_state.getHistoryLock());
        state.setConn(old_state.getConn());
        state.setExecuteScriptStack(new HashSet<String>(old_state.getExecuteScriptStack()));
        state.setInput(input);
        initMethodMap();
    }

    public State getState() {
        return state;
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
                if (state.getUsername() == null) {
                    res += "Вы не авторизованы";
                } else {
                    res += commandMap.get(cmd).exec(args).getMessage();
                    if (cmd == "exit") {
                        break;
                    }    
                }
            } else if (unauthorizedCommandMap.containsKey(cmd)) {
                res += unauthorizedCommandMap.get(cmd).exec(args).getMessage();
            } else {
                res += "Введена некорректная команда. Для просмотра списка команд ввеедите help";
            }
            res += '\n';
            System.out.printf("executing %s\n", cmd);
        }
        return res;
    }

    private boolean checkCredentials(Request req) {
        return new LoginCommand(state).validCreds(req.getCredentials());
    }

    public Response run(Request req) {
        if (commandMap.containsKey(req.getCommand())) {
            if (!checkCredentials(req)) {
                return new Response("Вы не авторизованы", false);
            }
            return commandMap.get(req.getCommand()).exec(req);
        }
        if (unauthorizedCommandMap.containsKey(req.getCommand())) {
            return unauthorizedCommandMap.get(req.getCommand()).exec(req);
        }
        return new Response("Введена некорректная команда. Для просмотра списка команд ввеедите help", false);
    }
}
