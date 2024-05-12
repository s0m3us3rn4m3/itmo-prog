package com.example.l9.common.request;

import com.example.l9.common.collection.Credentials;

public class LoginRequest extends Request {
    public LoginRequest(Credentials c) {
        command = "login";
        credentials = c;
    }
}
