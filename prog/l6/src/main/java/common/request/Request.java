package common.request;

import java.io.Serializable;

public class Request implements Serializable {
    protected String command;

    public String getCommand() {
        return command;
    }
}
