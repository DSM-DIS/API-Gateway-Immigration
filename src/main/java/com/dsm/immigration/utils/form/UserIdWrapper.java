package com.dsm.immigration.utils.form;

public class UserIdWrapper {
    private String userId;

    public UserIdWrapper() {}
    public UserIdWrapper(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
