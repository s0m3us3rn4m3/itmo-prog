package objects.commands;

import objects.Command;
import objects.State;

/**
 * exit : завершить программу
 */
public class ExitCommand extends Command {
    public ExitCommand(State state) {
        super(state);
    }

    @Override
    public String getDescription() {
        return "exit : завершить программу";
    }

    @Override
    public int exec(String[] args) {
        addToHistory(args[0]);
        return 1;
    }
}
