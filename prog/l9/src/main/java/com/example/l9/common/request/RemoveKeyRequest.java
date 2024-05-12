package com.example.l9.common.request;

public class RemoveKeyRequest extends Request {
    private String key;

    public RemoveKeyRequest(String k) {
        command = "remove_key";
        key = k;
    }    

    public String getKey() {
        return key;
    }
}
