package com.example.db;

import java.util.Objects;

public class DBProperties {
    private static final String URL1 = "jdbc:postgresql://localhost:5432/database";
    private static final String USER = "postgres";
    private static final String PASSWORD = "admin";

    private static DBProperties INSTANCE;

    private String url;
    private String user;
    private String password;

    private DBProperties() {}

    private void init() {
        url = URL1;
        user = USER;
        password = PASSWORD;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public static DBProperties getProperties()
    {
        if(Objects.isNull(INSTANCE))
        {
            INSTANCE = new DBProperties();
            INSTANCE.init();
        }

        return INSTANCE;
    }
}
