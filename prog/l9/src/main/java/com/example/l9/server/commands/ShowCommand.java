package com.example.l9.server.commands;

import com.example.l9.common.request.Request;
import com.example.l9.common.response.Response;
import com.example.l9.common.response.ShowResponse;
import com.example.l9.server.State;

/**
 * show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении
 */
public class ShowCommand extends Command {
    public ShowCommand(State state) {
        super(state);
    }

    @Override
    public String getDescription() {
        return "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }

    class Helper {
        private String res = "";

        public String exec(State state) {
            res = "";
            state.getCollection().values().stream().sorted((m1, m2) -> m1.getName().compareTo(m2.getName())).forEach(movie -> res += movie.toString() + '\n');
            return res;
        }
    }

    @Override
    public Response exec(String[] args) {
        addToHistory(args[0]);
        state.getLock().readLock().lock();
        try {
            ShowResponse res = new ShowResponse();
            res.setMessage(new Helper().exec(state));
            res.setSuccess(true);
            res.setMovies(state.getCollection().values().stream().toList());
            return res;
        } finally {
            state.getLock().readLock().unlock();
        }
    }

    @Override
    public Response exec(Request req) {
        String[] args = {req.getCommand()};
        return exec(args);
    }
}
