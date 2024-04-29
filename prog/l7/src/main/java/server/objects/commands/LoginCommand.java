package server.objects.commands;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import common.objects.Credentials;
import common.request.LoginRequest;
import common.request.Request;
import common.response.Response;
import server.Utils;
import server.objects.Command;
import server.objects.State;

public class LoginCommand extends Command {
    public LoginCommand(State state) {
        super(state);
    }

    public boolean validCreds(Credentials creds) {
        try {
            String hashedPassword = Utils.hashPassword(creds.getPassword());
            PreparedStatement s = state.getConn().prepareStatement("SELECT username FROM users WHERE username=? AND password=?");
            s.setString(1, creds.getLogin());
            s.setString(2, hashedPassword);
            ResultSet res = s.executeQuery();
            return res.next();
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String getDescription() {
        return "login: Проверить, что пользователь с заданными логином и паролем существует";
    }

    @Override
    public Response exec(String[] args) {
        addToHistory(args[0]);
        Credentials creds = Credentials.readFromScanner(state.getInput(), false);
        state.getLock().readLock().lock();
        try {
            if (validCreds(creds) == false) {
                return new Response("Неправильный логин или пароль", false);
            }
            state.setUsername(creds.getLogin());
            return success();
        } finally {
            state.getLock().readLock().unlock();
        }
    }

    @Override
    public Response exec(Request req) {
        addToHistory(req.getCommand());
        LoginRequest r = (LoginRequest)req;
        state.getLock().readLock().lock();
        try {
            if (validCreds(r.getCredentials()) == false) {
                return new Response("Неправильный логин или пароль", false);
            }
            return success();
        } finally {
            state.getLock().readLock().unlock();
        }
    }
}
