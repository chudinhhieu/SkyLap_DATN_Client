package com.example.skylap_datn_md03.data.models;

import java.io.Serializable;

public class MyAuth implements Serializable {
    private boolean success;
    private String message;

    private String value;

    public MyAuth(boolean success, String message, String value) {
        this.success = success;
        this.message = message;
        this.value = value;
    }

    public MyAuth() {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
