package server.interfaces;

import common.request.Request;
import common.response.Response;

/**
 * Интерфейс для команды
 */
public interface CommandInterface {
    public String getDescription();
    public Response exec(String[] args);
    public Response exec(Request req);
}
