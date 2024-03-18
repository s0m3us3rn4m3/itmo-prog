package objects.commands;

import objects.Command;
import objects.State;

/**
 * save : сохранить коллекцию в файл
 */
public class SaveCommand extends Command {
    public SaveCommand(State state) {
        super(state);
    }

    @Override
    public String getDescription() {
        return "save : сохранить коллекцию в файл";
    }

    @Override
    public int exec(String[] args) {
        addToHistory(args[0]);
        try {
            state.saveToFile();
        } catch (Exception e) {
            System.out.printf("Не удалось сохранить: %s\n", e);
        }
        return 0;
    }
}
