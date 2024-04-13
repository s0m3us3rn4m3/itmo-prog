package server.objects.commands;

import common.request.RemoveKeyRequest;
import common.request.Request;
import server.objects.Command;
import server.objects.State;

/**
 * remove_key key : удалить элемент из коллекции по его ключу
 */
public class RemoveKeyCommand extends Command {
    public RemoveKeyCommand(State state) {
        super(state);
    }

    @Override
    public String getDescription() {
        return "remove_key key : удалить элемент из коллекции по его ключу";
    }

    @Override
    public String exec(String[] args) {
        addToHistory(args[0]);
        if (args.length < 2) {
            return error();
        }
        String key = args[1];
        if (!state.getCollection().containsKey(key)) {
            return "Нет такого ключа";
        } else {
            state.getCollection().remove(key);
        }
        return "ok";
    }

    @Override
    public String exec(Request req) {
        RemoveKeyRequest r = (RemoveKeyRequest)req;
        String[] args = {r.getCommand(), r.getKey()};
        return exec(args);
    }
}
