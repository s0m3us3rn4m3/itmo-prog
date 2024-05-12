package com.example.l9.server.commands;

import java.util.Deque;

import com.example.l9.common.response.Response;
import com.example.l9.server.State;

public abstract class Command implements CommandInterface {
    protected State state;

    public Command() {
        state = new State();
    }

    public Command(State state) {
        this.state = state;
    }

    protected void addToHistory(String cmd) {
        state.getHistoryLock().writeLock().lock();
        try {
            Deque<String> history = state.getHistory();
            history.addFirst(cmd);
            if (history.size() > 7) {
                history.removeLast();
            }
        } finally {
            state.getHistoryLock().writeLock().unlock();
        }
    }

    protected Response error() {
        return new Response("Введена некорректная команда. Для просмотра списка команд ввеедите help", false);
    }

    protected Response success() {
        return new Response("ok", true);
    }
}
