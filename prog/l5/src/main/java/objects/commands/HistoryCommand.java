package objects.commands;

import objects.Command;
import objects.State;

/**
 * history : вывести последние 7 команд (без их аргументов)
 */
public class HistoryCommand extends Command {
    public HistoryCommand(State state) {
        super(state);
    }

    @Override
    public String getDescription() {
        return "history : вывести последние 7 команд (без их аргументов)";
    }

    @Override
    public int exec(String[] args) {
        for (String cmd : state.getHistory()) {
            System.out.println(cmd);
        }
        addToHistory(args[0]);
        return 0;
    }
}
