package br.com.sovis.ordersmanager.dao;

import java.sql.SQLException;

import br.com.sovis.ordersmanager.db.Database;
import br.com.sovis.ordersmanager.model.Product;
import totalcross.sql.Connection;
import totalcross.sql.PreparedStatement;
import totalcross.sql.ResultSet;
import totalcross.util.Vector;

public class ProductDAO {

    private Connection connection;

    public ProductDAO() {
        try {
            this.connection = Database.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(Product product) throws Exception {

        PreparedStatement ps = connection.prepareStatement("INSERT INTO product (name, description, price) VALUES (?, ?, ?)");
        ps.setString(1, product.getName());
        ps.setString(2, product.getDescription());
        ps.setDouble(3, product.getPrice());

        ps.executeUpdate();
        ps.close();
                
    }

    public Product[] findAll() throws Exception {

        PreparedStatement ps = connection.prepareStatement("SELECT * FROM product");

        ResultSet rs = ps.executeQuery();

        Vector list = new Vector();

        while (rs.next()) {

            Product product = new Product();
            product.setId(rs.getInt("id"));
            product.setName(rs.getString("name"));
            product.setDescription(rs.getString("description"));
            product.setPrice(rs.getDouble("price"));

            list.addElement(product);
        }

        rs.close();
        ps.close();

        Product[] products = new Product[list.size()];
        list.copyInto(products);

        return products;
    }

    public void removeProduct(int productId) throws Exception {

        PreparedStatement ps = connection.prepareStatement("DELETE FROM product WHERE id = ?");

        ps.setInt(1, productId);

        ps.executeUpdate();
        ps.close();

    }

}
