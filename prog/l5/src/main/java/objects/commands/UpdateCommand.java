package objects.commands;

import objects.Command;
import objects.Movie;
import objects.State;

/**
 * update id {element} : обновить значение элемента коллекции, id которого равен заданному
 */
public class UpdateCommand extends Command {
    public UpdateCommand(State state) {
        super(state);
    }

    @Override
    public String getDescription() {
        return "update id {element} : обновить значение элемента коллекции, id которого равен заданному";
    }

    @Override
    public int exec(String[] args) {
        addToHistory(args[0]);
        if (args.length < 2) {
            error();
            return 0;
        }
        String key = args[1];
        if (!state.getCollection().containsKey(key)) {
            System.out.println("Нет такого ключа");
        } else {
            Movie movie = Movie.readFromScanner(state.getInput());
            state.getCollection().replace(key, movie);
        }
        return 0;
    }
}
