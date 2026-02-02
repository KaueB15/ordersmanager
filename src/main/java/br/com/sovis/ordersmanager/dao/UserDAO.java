package br.com.sovis.ordersmanager.dao;

import java.sql.SQLException;

import br.com.sovis.ordersmanager.db.Database;
import br.com.sovis.ordersmanager.model.User;
import totalcross.sql.Connection;
import totalcross.sql.PreparedStatement;
import totalcross.sql.ResultSet;

public class UserDAO {

    private Connection connection;

    public UserDAO() {
        try {
            this.connection = Database.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(User user) throws Exception {

        PreparedStatement ps = connection.prepareStatement("INSERT INTO users (email, password, admin) VALUES (?, ?, ?)");
        ps.setString(1, user.getEmail());
        ps.setString(2, user.getPassword());
        ps.setInt(3, 0);
        ps.executeUpdate();
        ps.close();

    }

    public User autenticate(String email, String password) throws Exception {

        PreparedStatement ps = connection.prepareStatement("SELECT * FROM users WHERE email = ? AND password = ?");
        ps.setString(1, email);
        ps.setString(2, password);

        ResultSet rs = ps.executeQuery();
        if(rs.next()) {
            User user = new User();
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setAdmin(rs.getInt("admin"));
            return user;
        }

        rs.close();
        ps.close();

        return null;
        
    }

}
