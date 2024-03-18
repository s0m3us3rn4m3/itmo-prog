package objects.commands;

import objects.Command;
import objects.Movie;
import objects.State;

/**
 * replace_if_lowe key {element} : заменить значение по ключу, если новое значение меньше старого
 */
public class ReplaceIfLoweCommand extends Command {
    public ReplaceIfLoweCommand(State state) {
        super(state);
    }

    @Override
    public String getDescription() {
        return "replace_if_lowe key {element} : заменить значение по ключу, если новое значение меньше старого";
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
            System.out.println("Ключ не найден");
            return 0;
        }
        Movie new_m = Movie.readFromScanner(state.getInput());
        Movie old_m = state.getCollection().get(key);
        if (old_m.compareTo(new_m) > 0) {
            state.getCollection().replace(key, new_m);
        }
        return 0;
    }
}
