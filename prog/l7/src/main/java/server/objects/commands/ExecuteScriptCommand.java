package server.objects.commands;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import common.request.ExecuteScriptRequest;
import common.request.Request;
import common.response.Response;
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
    public Response exec(String[] args) {
        addToHistory(args[0]);
        if (args.length < 2) {
            return error();
        }

        String scriptPath = args[1];
        try {
            File file = new File(scriptPath);
            if (state.getExecuteScriptStack().contains(file.getAbsolutePath())) {
                return new Response("Recursion detected", false);
            }
            FileInputStream f = new FileInputStream(scriptPath);
            Scanner s = new Scanner(new BufferedInputStream(f));
            try {
                Executor e = new Executor(s, state);
                e.getState().getExecuteScriptStack().add(file.getAbsolutePath());
                String res = e.run();
                return new Response(res, true);
            } finally {
                s.close();
            }
        } catch (FileNotFoundException e) {
            return new Response("Файл не найден", false);
        }
    }

    @Override
    public Response exec(Request req) {
        ExecuteScriptRequest r = (ExecuteScriptRequest)req;
        String[] args = {r.getCommand(), r.getScriptPath()};
        return exec(args);
    }
}
