package br.com.sovis.ordersmanager.db;

import totalcross.db.sqlite.SQLiteConnection;
import totalcross.sql.Connection;
import totalcross.sql.PreparedStatement;
import totalcross.sql.Statement;

public class Database {
    private static Connection connection;

    public static void open() throws Exception {
        if(connection == null) {
            connection = new SQLiteConnection("jdbc:sqlite:", "order.db");
        }
    }

    public static void createTables() throws Exception {
        Statement st = connection.createStatement();

        st.execute(
            "CREATE TABLE IF NOT EXISTS users (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "email TEXT NOT NULL UNIQUE," +
            "password TEXT NOT NULL)"
        );

        st.execute(
            "CREATE TABLE IF NOT EXISTS customer (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "nome TEXT NOT NULL," +
            "email TEXT," +
            "phone TEXT," +
            "createdAt TEXT)"
        );

        
        st.execute(
            "CREATE TABLE IF NOT EXISTS product (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "nome TEXT NOT NULL," +
            "description TEXT," +
            "price REAL NOT NULL)"
        );
        
        st.execute(
            "CREATE TABLE IF NOT EXISTS orders (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "id_customer INTEGER NOT NULL," +
            "total_price REAL," +
            "order_date TEXT," +
            "status TEXT," +
            "FOREIGN KEY (id_customer) REFERENCES customer(id))"
        );
        
        st.execute(
            "CREATE TABLE IF NOT EXISTS product_order (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "id_order INTEGER NOT NULL," +
            "id_product INTEGER NOT NULL," +
            "price REAL," +
            "order_date TEXT," +
            "status TEXT," +
            "FOREIGN KEY (id_order) REFERENCES orders(id)," +
            "FOREIGN KEY (id_product) REFERENCES product(id))"
        );
    }

    public static void insertAdminUser() throws Exception {
        String sql =
            "INSERT OR IGNORE INTO users (email, password) VALUES (?, ?)";

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, "admin");
        ps.setString(2, "123");
        ps.executeUpdate();
        ps.close();
    }

    public static Connection getConnection() {
        return connection;
    }

}
