package objects.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import objects.Command;
import objects.Movie;
import objects.State;

/**
 * print_descending : вывести элементы коллекции в порядке убывания
 */
public class PrintDescending extends Command {
    public PrintDescending(State state) {
        super(state);
    }

    @Override
    public String getDescription() {
        return "print_descending : вывести элементы коллекции в порядке убывания";
    }

    @Override
    public int exec(String[] args) {
        addToHistory(args[0]);
        List<Movie> c = new ArrayList<Movie>(state.getCollection().values());
        Collections.sort(c, (m1, m2) -> -m1.compareTo(m2));
        for (Movie m : c) {
            System.out.println(m);
        }
        return 0;
    }
}
