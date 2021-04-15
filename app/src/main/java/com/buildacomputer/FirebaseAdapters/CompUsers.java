package com.buildacomputer.FirebaseAdapters;

public class CompUsers {
    private String name;
    private String username;
    private String password;
    private String userID;

    public CompUsers(String name, String username, String password,String id) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.userID = id;
    }

    public CompUsers() {
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return userID;
    }

    public void setId(String id) {
        this.userID = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
