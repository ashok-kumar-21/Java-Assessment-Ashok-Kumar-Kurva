package com.ford.assessment.connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionClass {
    Connection conn;
    public Connection getMyConnection() {
        String url="jdbc:h2:tcp://localhost:9092/~/test";
        String user = "sa";
        String password = "h2@123456";
        try {
            Class.forName("org.h2.Driver");
            conn= DriverManager.getConnection(url,user,password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return conn;
    }


}
