package server.interfaces;

import common.request.Request;

/**
 * Интерфейс для команды
 */
public interface CommandInterface {
    public String getDescription();
    public String exec(String[] args);
    public String exec(Request req);
}
