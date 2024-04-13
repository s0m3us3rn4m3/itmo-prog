package client.commands;

import common.request.RemoveKeyRequest;
import common.request.Request;

public class RemoveKeyCommand implements Command {
    @Override
    public Request makeRequest(String[] args) {
        if (args.length < 2) {
            System.out.println("usage: remove_key {key}");
            return null;
        }
        return new RemoveKeyRequest(args[1]);
    }
}
