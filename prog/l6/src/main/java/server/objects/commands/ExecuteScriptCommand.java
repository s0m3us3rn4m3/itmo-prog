package server.objects.commands;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;

import common.request.ExecuteScriptRequest;
import common.request.Request;
import server.objects.Command;
import server.objects.Executor;
import server.objects.State;

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
    public String exec(String[] args) {
        addToHistory(args[0]);
        if (args.length < 2) {
            return error();
        }

        String scriptPath = args[1];
        try {
            File file = new File(scriptPath);
            if (state.getExecuteScriptStack().contains(file.getAbsolutePath())) {
                return "Recursion detected";
            }
            state.getExecuteScriptStack().add(file.getAbsolutePath());
            FileInputStream f = new FileInputStream(scriptPath);
            Scanner s = new Scanner(new BufferedInputStream(f));
            try {
                Scanner oldInput = state.getInput();
                Set<String> oldStack = state.getExecuteScriptStack();
                Executor e = new Executor(s, state);
                String res = e.run();
                state.setInput(oldInput);
                state.setExecuteScriptStack(oldStack);
                state.getExecuteScriptStack().remove(file.getAbsolutePath());
                return res;
            } finally {
                s.close();
            }
        } catch (FileNotFoundException e) {
            return "Файл не найден";
        }
    }

    @Override
    public String exec(Request req) {
        ExecuteScriptRequest r = (ExecuteScriptRequest)req;
        String[] args = {r.getCommand(), r.getScriptPath()};
        return exec(args);
    }
}
