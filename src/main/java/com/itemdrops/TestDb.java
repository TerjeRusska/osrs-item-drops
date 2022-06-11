package com.itemdrops;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TestDb {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Connection conn = null;
        Statement stmt = null;

        Class.forName("org.h2.Driver");
        // ;OLD_INFORMATION_SCHEMA=TRUE
        conn = DriverManager.getConnection("jdbc:h2:./src/main/resources/itemdrops", "", "");
        conn.close();
    }
}
