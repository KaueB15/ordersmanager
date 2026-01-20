package br.com.sovis.ordersmanager.dao;

import br.com.sovis.ordersmanager.db.Database;
import br.com.sovis.ordersmanager.model.User;
import totalcross.sql.Connection;
import totalcross.sql.PreparedStatement;
import totalcross.sql.ResultSet;

public class UserDAO {

    private Connection connection;

    public UserDAO() {
        this.connection = Database.getConnection();
    }

    public void insert(User user) throws Exception {

        PreparedStatement ps = connection.prepareStatement("INSERT INTO users (email, password) VALUES (?, ?)");
        ps.setString(1, user.getEmail());
        ps.setString(2, user.getPassword());
        ps.executeUpdate();
        ps.close();

    }

    public boolean autenticate(String email, String password) throws Exception {

        PreparedStatement ps = connection.prepareStatement("SELECT * FROM users WHERE email = ? AND password = ?");
        ps.setString(1, email);
        ps.setString(2, password);

        ResultSet rs = ps.executeQuery();
        boolean login = rs.next();

        rs.close();
        ps.close();

        return login;
        
    }

}
