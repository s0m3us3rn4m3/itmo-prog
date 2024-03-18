package objects.commands;

import objects.Command;
import objects.Movie;
import objects.State;

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

    @Override
    public int exec(String[] args) {
        addToHistory(args[0]);
        Movie m = Movie.readFromScanner(state.getInput());
        state.getCollection().entrySet().removeIf(entry -> entry.getValue().compareTo(m) > 0);
        return 0;
    }
}
