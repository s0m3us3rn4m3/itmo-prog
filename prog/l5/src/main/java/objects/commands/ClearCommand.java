package objects.commands;

import objects.Command;
import objects.State;

/**
 * clear : очистить коллекцию
 */
public class ClearCommand extends Command {
    public ClearCommand(State state) {
        super(state);
    }

    @Override
    public String getDescription() {
        return "clear : очистить коллекцию";
    }

    @Override
    public int exec(String[] args) {
        addToHistory(args[0]);
        state.getCollection().clear();
        return 0;
    }
}
