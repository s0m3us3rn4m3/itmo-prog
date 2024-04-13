package client.commands;

import common.request.ExecuteScriptRequest;
import common.request.Request;

public class ExecuteScriptCommand implements Command {
    @Override
    public Request makeRequest(String[] args) {
        if (args.length < 2) {
            System.out.println("usage: execute_script {script_path}");
            return null;
        }
        return new ExecuteScriptRequest(args[1]);
    }
}
