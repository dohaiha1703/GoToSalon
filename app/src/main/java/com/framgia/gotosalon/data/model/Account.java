package com.framgia.gotosalon.data.model;

public class Account {
    private String mEmail;
    private String mPassword;
    private boolean mRemember;
    private String mRePassword;
    private String mUsername;

    public Account() {

    }

    public Account(String email, String password) {

        mEmail = email;
        mPassword = password;
    }

    public Account(String email, String password, boolean remember) {
        mEmail = email;
        mPassword = password;
        mRemember = remember;
    }

    public Account(String email, String password, String rePassword, String username) {
        mEmail = email;
        mPassword = password;
        mRePassword = rePassword;
        mUsername = username;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public boolean isRemember() {
        return mRemember;
    }

    public void setRemember(boolean remember) {
        mRemember = remember;
    }

    public String getRePassword() {
        return mRePassword;
    }

    public void setRePassword(String rePassword) {
        mRePassword = rePassword;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }
}
