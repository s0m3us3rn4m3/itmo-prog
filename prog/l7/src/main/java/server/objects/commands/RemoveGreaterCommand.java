package server.objects.commands;

import common.objects.Movie;
import common.request.RemoveGreaterRequest;
import common.request.Request;
import common.response.Response;
import server.Utils;
import server.objects.Command;
import server.objects.State;

/**
 * remove_greater {element} : удалить из коллекции все элементы, превышающие заданный
 */
public class RemoveGreaterCommand extends Command {
    public RemoveGreaterCommand(State state) {
        super(state);
    }

    @Override
    public String getDescription() {
        return "remove_greater {element} : удалить из коллекции все элементы, превышающие заданный";
    }

    private Response filterCollection(String username, Movie m) {
        state.getLock().writeLock().lock();
        try {
            state.getCollection().entrySet().stream().filter(entry -> entry.getValue().compareTo(m) > 0).forEach(entry -> {
                Utils.deleteMovieFromDBByID(state.getConn(), username, m.getId());
            });
            state.getCollection().entrySet().removeIf(entry -> entry.getValue().compareTo(m) > 0);
            return success();
        } finally {
            state.getLock().writeLock().unlock();
        }
    }

    @Override
    public Response exec(String[] args) {
        addToHistory(args[0]);
        Movie m = Movie.readFromScanner(state.getInput(), false);
        return filterCollection(state.getUsername(), m);
    }

    @Override
    public Response exec(Request req) {
        RemoveGreaterRequest r = (RemoveGreaterRequest)req;
        addToHistory(r.getCommand());
        return filterCollection(r.getCredentials().getLogin(), r.getMovie());
    }
}
