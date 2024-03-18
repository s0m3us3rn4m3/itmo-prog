package objects.commands;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import objects.Command;
import objects.Executor;
import objects.State;

/**
 * execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.
 */
public class ExecuteScriptCommand extends Command {
    public ExecuteScriptCommand(State state) {
        super(state);
    }

    @Override
    public String getDescription() {
        return "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.";
    }
    
    @Override
    public int exec(String[] args) {
        addToHistory(args[0]);
        if (args.length < 2) {
            error();
            return 0;
        }

        String scriptPath = args[1];
        try {
            File file = new File(scriptPath);
            if (state.getExecuteScriptStack().contains(file.getAbsolutePath())) {
                System.out.println("Recursion detected");
                return 0;
            }
            state.getExecuteScriptStack().add(file.getAbsolutePath());
            FileInputStream f = new FileInputStream(scriptPath);
            Scanner s = new Scanner(new BufferedInputStream(f));
            try {
                Scanner old_input = state.getInput();
                Executor e = new Executor(s, state);
                e.process(false);
                state.setInput(old_input);
                state.getExecuteScriptStack().remove(file.getAbsolutePath());
            } finally {
                s.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден");
        }
        return 0;
    }


}
