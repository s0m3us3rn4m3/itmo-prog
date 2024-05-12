package com.example.l9.server.commands;


import com.example.l9.common.request.Request;
import com.example.l9.common.response.Response;
import com.example.l9.server.Utils;
import com.example.l9.server.State;

/**
 * info : вывести в стандартный поток вывода информацию о коллекции
 */
public class InfoCommand extends Command {
    public InfoCommand(State state) {
        super(state);
    }

    @Override
    public String getDescription() {
        return "info : вывести в стандартный поток вывода информацию о коллекции";
    }

    private Response info(String username) {
        state.getLock().readLock().lock();
        try {
            Integer cnt = Utils.countUserMovies(state.getConn(), username);
            if (cnt == null) {
                return new Response("Ошибка", false);
            }
            return new Response("Тип: TreeMap<String, Movie>\n" + 
                String.format("Количество элементов: %d\n", cnt), true);
        } finally {
            state.getLock().readLock().unlock();
        }
    }

    @Override
    public Response exec(String[] args) {
        addToHistory(args[0]);
        return info(state.getUsername());
    }

    @Override
    public Response exec(Request req) {
        addToHistory(req.getCommand());
        return info(req.getCredentials().getLogin());
    }
}
