package common.request;

public class ExecuteScriptRequest extends Request {
    private String scriptPath;

    public ExecuteScriptRequest(String filename) {
        command = "execute_script";
        scriptPath = filename;
    }

    public String getScriptPath() {
        return scriptPath;
    }
}
