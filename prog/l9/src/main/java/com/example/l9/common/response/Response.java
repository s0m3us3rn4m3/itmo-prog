package com.example.l9.common.response;

import java.io.Serializable;

public class Response implements Serializable {
    private String message;
    private boolean success;

    public Response() {}

    public Response(String msg, boolean success) {
        message = msg;
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean getSuccess() {
        return success;
    }
}
