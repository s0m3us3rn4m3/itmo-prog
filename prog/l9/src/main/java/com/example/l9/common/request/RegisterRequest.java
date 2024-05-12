package com.example.l9.common.request;

import com.example.l9.common.collection.Credentials;

public class RegisterRequest extends Request {
    public RegisterRequest(Credentials c) {
        command = "register";
        credentials = c;
    }
}
