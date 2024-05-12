package com.example.l9.client.commands;

import com.example.l9.common.request.ExecuteScriptRequest;
import com.example.l9.common.request.Request;

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
