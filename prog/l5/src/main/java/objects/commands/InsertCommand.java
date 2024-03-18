package objects.commands;

import objects.Command;
import objects.Movie;
import objects.State;

/**
 * insert key {element} : добавить новый элемент с заданным ключом
 */
public class InsertCommand extends Command {
    public InsertCommand(State state) {
        super(state);
    }
    
    public String getDescription() {
        return "insert key {element} : добавить новый элемент с заданным ключом";
    }

    public int exec(String[] args) {
        addToHistory(args[0]);
        if (args.length < 2) {
            error();
            return 0;
        }
        String key = args[1];
        if (state.getCollection().containsKey(key)) {
            System.out.println("Такой ключ уже существует");
            return 0;
        }
        Movie movie = Movie.readFromScanner(state.getInput());
        state.getCollection().put(key, movie);
        return 0;
    }
}
