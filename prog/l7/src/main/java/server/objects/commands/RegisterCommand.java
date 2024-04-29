package server.objects.commands;

import java.sql.PreparedStatement;

import common.objects.Credentials;
import common.request.RegisterRequest;
import common.request.Request;
import common.response.Response;
import server.Utils;
import server.objects.Command;
import server.objects.State;

public class RegisterCommand extends Command {
    public RegisterCommand(State state) {
        super(state);
    }

    private boolean register(Credentials creds) {
        try {
            if (Utils.userExists(state.getConn(), creds.getLogin())) {
                return false;
            }
            PreparedStatement s = state.getConn().prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)");
            s.setString(1, creds.getLogin());
            s.setString(2, Utils.hashPassword(creds.getPassword()));
            int res = s.executeUpdate();
            return res == 1;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String getDescription() {
        return "register: Зарегистрировать пользователя";
    }

    @Override
    public Response exec(String[] args) {
        addToHistory(args[0]);
        Credentials creds = Credentials.readFromScanner(state.getInput(), false);
        state.getLock().writeLock().lock();
        try {
            if (Utils.userExists(state.getConn(), creds.getLogin())) {
                return new Response("Пользователь с таким логином уже существует", false);
            }
            if (register(creds) == false) {
                return new Response("Ошибка", false);
            }
            state.setUsername(creds.getLogin());
            return success();
        } finally {
            state.getLock().writeLock().unlock();
        }
    }

    @Override
    public Response exec(Request req) {
        addToHistory(req.getCommand());
        RegisterRequest r = (RegisterRequest)req;
        state.getLock().writeLock().lock();
        try {
            if (Utils.userExists(state.getConn(), r.getCredentials().getLogin())) {
                return new Response("Пользователь с таким логином уже существует", false);
            }
            if (register(r.getCredentials()) == false) {
                return new Response("Ошибка", false);
            }
            return success();
        } finally {
            state.getLock().writeLock().unlock();
        }
    }
}
