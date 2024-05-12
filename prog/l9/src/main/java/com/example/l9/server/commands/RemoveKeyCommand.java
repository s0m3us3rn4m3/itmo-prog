package com.example.l9.server.commands;

import com.example.l9.common.request.RemoveKeyRequest;
import com.example.l9.common.request.Request;
import com.example.l9.common.response.Response;
import com.example.l9.server.Utils;
import com.example.l9.server.State;

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

    private Response removeMovie(String username, String key) {
        state.getLock().writeLock().lock();
        try {
            if (!Utils.isOwner(state.getConn(), username, key)) {
                return new Response("Пользователь не является владельцем данной записи", false);
            }
            if (!Utils.deleteMovieFromDBByKey(state.getConn(), key)) {
                return new Response("Ошибка", false);
            }
            state.getCollection().remove(key);
            return success();
        } finally {
            state.getLock().writeLock().unlock();
        }
    }

    @Override
    public Response exec(String[] args) {
        addToHistory(args[0]);
        if (args.length < 2) {
            return error();
        }
        String key = args[1];
        if (!state.getCollection().containsKey(key)) {
            return new Response("Нет такого ключа", false);
        }
        return removeMovie(state.getUsername(), key);
    }

    @Override
    public Response exec(Request req) {
        RemoveKeyRequest r = (RemoveKeyRequest)req;
        addToHistory(r.getCommand());
        if (!state.getCollection().containsKey(r.getKey())) {
            return new Response("Нет такого ключа", false);
        }
        return removeMovie(r.getCredentials().getLogin(), r.getKey());
    }
}
