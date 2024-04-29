package common.response;

import java.io.Serializable;

public class Response implements Serializable {
    private String message;
    private boolean success;

    public Response(String msg, boolean success) {
        message = msg;
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public boolean getSuccess() {
        return success;
    }
}
