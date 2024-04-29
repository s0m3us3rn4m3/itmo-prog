package common.request;

import common.objects.Credentials;

public class RegisterRequest extends Request {
    public RegisterRequest(Credentials c) {
        command = "register";
        credentials = c;
    }
}
