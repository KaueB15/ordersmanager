package br.com.sovis.ordersmanager.dao;

import java.sql.SQLException;

import br.com.sovis.ordersmanager.db.Database;
import br.com.sovis.ordersmanager.model.UsersProduct;
import totalcross.sql.Connection;
import totalcross.sql.PreparedStatement;

public class UsersProductDAO {

    private Connection connection;

    public UsersProductDAO() {
        try {
            this.connection = Database.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(UsersProduct item) throws Exception {

        PreparedStatement ps = connection.prepareStatement(
            "INSERT INTO product_user (id_user, id_product) VALUES (?, ?)"
        );

        ps.setInt(1, item.getIdUser());
        ps.setInt(2, item.getIdProduct());

        ps.executeUpdate();
        ps.close();
    }

}
