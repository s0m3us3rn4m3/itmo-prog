package common.request;

import common.objects.Credentials;

public class LoginRequest extends Request {
    public LoginRequest(Credentials c) {
        command = "login";
        credentials = c;
    }
}
