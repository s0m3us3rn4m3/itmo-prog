package server.objects.commands;

import common.request.Request;
import common.response.Response;
import server.objects.Command;
import server.objects.State;

/**
 * print_descending : вывести элементы коллекции в порядке убывания
 */
public class PrintDescending extends Command {
    public PrintDescending(State state) {
        super(state);
    }

    @Override
    public String getDescription() {
        return "print_descending : вывести элементы коллекции в порядке убывания";
    }

    class Helper {
        private String res = "";

        public String exec(State state) {
            res = "";
            state.getCollection().values().stream().sorted((m1, m2) -> -m1.compareTo(m2)).forEach(movie -> res += movie.toString() + '\n');;
            return res;
        }
    }

    @Override
    public Response exec(String[] args) {
        addToHistory(args[0]);
        state.getLock().readLock().lock();
        try {
            return new Response(new Helper().exec(state), true);         
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
