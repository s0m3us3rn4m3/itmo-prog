package common.response;

import java.io.Serializable;

public class Response implements Serializable {
    public String message;

    public Response() {}

    public Response(String msg) {
        message = msg;
    }
}
