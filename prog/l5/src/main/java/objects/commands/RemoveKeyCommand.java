package objects.commands;

import objects.Command;
import objects.State;

/**
 * remove_key key : удалить элемент из коллекции по его ключу
 */
public class RemoveKeyCommand extends Command {
    public RemoveKeyCommand(State state) {
        super(state);
    }

    @Override
    public String getDescription() {
        return "remove_key key : удалить элемент из коллекции по его ключу";
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
            state.getCollection().remove(key);
        }
        return 0;
    }
}
