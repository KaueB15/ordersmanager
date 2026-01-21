package br.com.sovis.ordersmanager.dao;

import br.com.sovis.ordersmanager.db.Database;
import br.com.sovis.ordersmanager.model.Product;
import totalcross.sql.Connection;
import totalcross.sql.PreparedStatement;

public class ProductDAO {

    private Connection connection;

    public ProductDAO() {
        this.connection = Database.getConnection();
    }

    public void insert(Product product) throws Exception {

        PreparedStatement ps = connection.prepareStatement("INSERT INTO product (name, description, price) VALUES (?, ?, ?)");
        ps.setString(1, product.getName());
        ps.setString(2, product.getDescription());
        ps.setDouble(3, product.getPrice());

        ps.executeUpdate();
        ps.close();
                
    }

}
