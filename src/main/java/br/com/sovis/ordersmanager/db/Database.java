package br.com.sovis.ordersmanager.db;

import totalcross.db.sqlite.SQLiteConnection;
import totalcross.sql.Connection;

public class Database {
    private static Connection connection;

    public static void open() throws Exception {
        if(connection == null) {
            connection = new SQLiteConnection("jdbc:sqlite:", "order.db");
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
