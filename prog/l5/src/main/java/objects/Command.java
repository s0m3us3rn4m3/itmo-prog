package objects;

import java.util.Deque;

import interfaces.CommandInterface;

public abstract class Command implements CommandInterface {
    protected State state;

    public Command() {
        state = new State();
    }

    public Command(State state) {
        this.state = state;
    }

    protected void addToHistory(String cmd) {
        Deque<String> history = state.getHistory();
        history.addFirst(cmd);
        if (history.size() > 7) {
            history.removeLast();
        }
    }

    protected void error() {
        System.out.println("Введена некорректная команда. Для просмотра списка команд ввеедите help");
    }
}
