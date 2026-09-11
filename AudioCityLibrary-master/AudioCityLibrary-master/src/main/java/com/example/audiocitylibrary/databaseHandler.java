package com.example.audiocitylibrary;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import javax.xml.transform.Result;
import java.lang.annotation.Target;
import java.sql.*;
import java.sql.SQLException;

public class databaseHandler {
    public static Connection connectDb() {
        String jdbcUrl = "jdbc:mysql://192.166.219.220:3306/audiotowndatabase";
        String usernameDB = "audiotowndatabase";
        String password = "bazunia1212;";

        Connection connect = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  //ustawienie sterownika jdbc
            connect = DriverManager.getConnection(jdbcUrl, usernameDB, password);
            return connect;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
    public static void main(String[] args) {

    }
}