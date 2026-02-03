package br.com.sovis.ordersmanager.db;

import java.sql.SQLException;

import totalcross.db.sqlite.SQLiteUtil;
import totalcross.sql.Connection;
import totalcross.sql.PreparedStatement;
import totalcross.sql.Statement;
import totalcross.sys.Settings;

public class Database {

    private static SQLiteUtil connection;
    
    public static void open() throws SQLException {
        connection = new SQLiteUtil(Settings.appPath, "order.db");
    }

    public static void createTables() throws Exception {
        Statement st = connection.con().createStatement();

        st.execute(
            "CREATE TABLE IF NOT EXISTS users (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "email TEXT NOT NULL UNIQUE," +
            "admin INTEGER NOT NULL," +
            "password TEXT NOT NULL)"
        );

        st.execute(
            "CREATE TABLE IF NOT EXISTS customer (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "name TEXT NOT NULL," +
            "email TEXT," +
            "phone TEXT," +
            "createdAt TEXT)"
        );

        
        st.execute(
            "CREATE TABLE IF NOT EXISTS product (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "name TEXT NOT NULL," +
            "description TEXT," +
            "price REAL NOT NULL)"
        );
        
        st.execute(
            "CREATE TABLE IF NOT EXISTS orders (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "id_customer INTEGER NOT NULL," +
            "id_user INTEGER NOT NULL," +
            "total_price REAL," +
            "order_date TEXT," +
            "status TEXT," +
            "FOREIGN KEY (id_user) REFERENCES users(id)," +
            "FOREIGN KEY (id_customer) REFERENCES customer(id))"
        );
        
        st.execute(
            "CREATE TABLE IF NOT EXISTS product_order (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "id_order INTEGER NOT NULL," +
            "id_product INTEGER NOT NULL," +
            "price REAL," +
            "quantity INTEGER," +
            "FOREIGN KEY (id_order) REFERENCES orders(id)," +
            "FOREIGN KEY (id_product) REFERENCES product(id))"
        );

        st.execute(
            "CREATE TABLE IF NOT EXISTS product_user (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "id_user INTEGER NOT NULL," +
            "id_product INTEGER NOT NULL," +
            "FOREIGN KEY (id_user) REFERENCES users(id)," +
            "FOREIGN KEY (id_product) REFERENCES product(id))"
        );
    }

    public static void insertAdminUser() throws Exception {
        String sql =
            "INSERT OR IGNORE INTO users (email, password, admin) VALUES (?, ?, ?)";

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, "admin");
        ps.setString(2, "123");
        ps.setInt(3, 1);
        ps.executeUpdate();
        ps.close();
    }

    public static Connection getConnection() throws SQLException {
        return connection.con();
    }

}
