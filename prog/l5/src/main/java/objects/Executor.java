package objects;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import interfaces.CommandInterface;
import objects.commands.ClearCommand;
import objects.commands.CountLessThanGenreCommand;
import objects.commands.ExecuteScriptCommand;
import objects.commands.ExitCommand;
import objects.commands.FilterLessThanUsaBoxOffice;
import objects.commands.HistoryCommand;
import objects.commands.InfoCommand;
import objects.commands.InsertCommand;
import objects.commands.PrintDescending;
import objects.commands.RemoveGreaterCommand;
import objects.commands.RemoveKeyCommand;
import objects.commands.ReplaceIfLoweCommand;
import objects.commands.SaveCommand;
import objects.commands.ShowCommand;
import objects.commands.UpdateCommand;

/**
 * Класс, который хранит коллекцию и выполняет команды
 */
public class Executor {
    private Map<String, CommandInterface> commandMap;
    private State state;

    private void initMethodMap() {
        this.commandMap = new HashMap<String, CommandInterface>();
        commandMap.put("help", new CommandInterface() {
            public String getDescription() {
                return "help : вывести справку по доступным командам";
            }

            public int exec(String[] args) {
                for (CommandInterface command : commandMap.values()) {
                    System.out.println(command.getDescription());
                }        
                return 0;
            }
        });
        commandMap.put("info", new InfoCommand(state));
        commandMap.put("show", new ShowCommand(state));
        commandMap.put("insert", new InsertCommand(state));
        commandMap.put("update", new UpdateCommand(state));
        commandMap.put("remove_key", new RemoveKeyCommand(state));
        commandMap.put("clear", new ClearCommand(state));
        commandMap.put("save", new SaveCommand(state));
        commandMap.put("execute_script", new ExecuteScriptCommand(state));
        commandMap.put("exit", new ExitCommand(state));
        commandMap.put("remove_greater", new RemoveGreaterCommand(state));
        commandMap.put("history", new HistoryCommand(state));
        commandMap.put("replace_if_lowe", new ReplaceIfLoweCommand(state));
        commandMap.put("count_less_than_genre", new CountLessThanGenreCommand(state));
        commandMap.put("filter_less_than_usa_box_office", new FilterLessThanUsaBoxOffice(state));
        commandMap.put("print_descending", new PrintDescending(state));
    }


    public Executor(Scanner input, String fileToSave) {
        state = new State();
        state.setFileToSave(fileToSave);
        state.setInput(input);
        initMethodMap();

        try {
            state.parseFileToSave();
        } catch (Exception e) {
            System.out.printf("Не удалось загрузить файл: %s\n", e);
        }
    }

    public Executor(Scanner input, State old_state) {
        state = old_state;
        state.setInput(input);
        initMethodMap();
    }

    /**
     * Считывает и выполняет команды, пока не придет команда exit или не закроется
     * поток
     * 
     * @param print_prefix нужно ли выводить '>'
     */
    public void process(boolean print_prefix) {
        if (print_prefix) {
            System.out.print("> ");
        }
        while (state.getInput().hasNextLine()) {
            String line = state.getInput().nextLine();
            String[] args = line.split(" ");
            String cmd = args[0].toLowerCase();
            if (commandMap.containsKey(cmd)) {
                int res = commandMap.get(cmd).exec(args);
                if (res != 0) {
                    return;
                }
            } else {
                System.out.println("Введена некорректная команда. Для просмотра списка команд ввеедите help");
            }
            if (print_prefix) {
                System.out.print("> ");
            }
        }
    }
}
