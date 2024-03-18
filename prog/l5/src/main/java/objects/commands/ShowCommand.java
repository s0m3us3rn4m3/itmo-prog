package objects.commands;


import java.util.Map;

import objects.Command;
import objects.Movie;
import objects.State;

/**
 * show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении
 */
public class ShowCommand extends Command {
    public ShowCommand(State state) {
        super(state);
    }

    public String getDescription() {
        return "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }

    public int exec(String[] args) {
        addToHistory(args[0]);
        for (Map.Entry<String, Movie> entry : state.getCollection().entrySet()) {
            Movie movie = entry.getValue();
            System.out.println(movie);
        }
        return 0;
    }
}
