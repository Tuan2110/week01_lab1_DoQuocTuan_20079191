package com.edu.iuh.fit.week1.repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
    private Connection con;
    private static ConnectDB instance = null;
    private ConnectDB() throws ClassNotFoundException, SQLException {
        Class.forName("org.mariadb.jdbc.Driver");
        String url = "jdbc:mariadb://localhost:3307/mydb?createDatabaseIfNotExist=true";
        con = DriverManager.getConnection(url, "root", "sapassword");
    }
    public Connection getConnection() {
        return con;
    }

    public static ConnectDB getInstance() throws SQLException, ClassNotFoundException {
        if(instance == null )
            instance = new ConnectDB();
        return instance;
    }
}

