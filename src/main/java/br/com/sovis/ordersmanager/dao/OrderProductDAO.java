package br.com.sovis.ordersmanager.dao;

import java.sql.SQLException;

import br.com.sovis.ordersmanager.db.Database;
import br.com.sovis.ordersmanager.model.OrdersProduct;
import totalcross.sql.Connection;
import totalcross.sql.PreparedStatement;

public class OrderProductDAO {

    private Connection connection;

    public OrderProductDAO() {
        try {
            this.connection = Database.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(OrdersProduct item) throws Exception {

        PreparedStatement ps = connection.prepareStatement(
            "INSERT INTO product_order (id_order, id_product, price, quantity) VALUES (?, ?, ?, ?)"
        );

        ps.setInt(1, item.getidOrder());
        ps.setInt(2, item.getidProduct());
        ps.setInt(3, item.getQuantity());
        ps.setDouble(4, item.getValue());

        ps.executeUpdate();
        ps.close();
    }

}
