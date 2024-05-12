package com.example.l9.server.commands;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.example.l9.common.collection.Credentials;
import com.example.l9.common.request.LoginRequest;
import com.example.l9.common.request.Request;
import com.example.l9.common.response.Response;
import com.example.l9.server.State;

public class LoginCommand extends Command {
    public LoginCommand(State state) {
        super(state);
    }

    public boolean validCreds(Credentials creds) {
        try {
            PreparedStatement s = state.getConn().prepareStatement("SELECT username FROM users WHERE username=? AND password=?");
            s.setString(1, creds.getLogin());
            s.setString(2, creds.getPassword());
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
            if (!validCreds(creds)) {
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
            if (!validCreds(r.getCredentials())) {
                return new Response("Неправильный логин или пароль", false);
            }
            return success();
        } finally {
            state.getLock().readLock().unlock();
        }
    }
}
