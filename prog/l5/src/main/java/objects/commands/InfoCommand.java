package objects.commands;


import objects.Command;
import objects.State;

/**
 * info : вывести в стандартный поток вывода информацию о коллекции
 */
public class InfoCommand extends Command {
    public InfoCommand(State state) {
        super(state);
    }

    @Override
    public String getDescription() {
        return "info : вывести в стандартный поток вывода информацию о коллекции";
    }

    @Override
    public int exec(String[] args) {
        addToHistory(args[0]);
        System.out.println("Тип: TreeMap<String, Movie>\n"
                + String.format("Количество элементов: %d\n", state.getCollection().size()));
        return 0;
    }
}
