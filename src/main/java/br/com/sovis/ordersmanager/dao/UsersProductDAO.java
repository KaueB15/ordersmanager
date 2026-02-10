package br.com.sovis.ordersmanager.dao;

import java.sql.SQLException;

import br.com.sovis.ordersmanager.db.Database;
import br.com.sovis.ordersmanager.model.Product;
import br.com.sovis.ordersmanager.model.UsersProduct;
import totalcross.sql.Connection;
import totalcross.sql.PreparedStatement;
import totalcross.sql.ResultSet;
import totalcross.util.Vector;

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

    public Product[] findByUserId(int userId) throws Exception {
        String sql =
            "SELECT DISTINCT pu.id_product, p.name, p.price, p.description " +
            "FROM product_user pu " +
            "JOIN product p ON p.id = pu.id_product " +
            "WHERE pu.id_user = ?";

        PreparedStatement ps = Database.getConnection().prepareStatement(sql);

        ps.setInt(1, userId);

        ResultSet rs = ps.executeQuery();

        Vector items = new Vector();

        while (rs.next()) {
            Product item = new Product();
            item.setId(rs.getInt("id_product"));
            item.setName(rs.getString("name"));
            item.setDescription(rs.getString("description"));
            item.setPrice(rs.getDouble("price"));

            items.addElement(item);
        }

        rs.close();
        ps.close();

        Product[] result =
            new Product[items.size()];

        items.copyInto(result);

        return result;
    }

    public boolean productAlreadyAssocieated(UsersProduct item) throws Exception {
        PreparedStatement ps = connection.prepareStatement(
            "SELECT * FROM product_user WHERE id_user = ? AND id_product = ?"
        );

        ps.setInt(1, item.getIdUser());
        ps.setInt(2, item.getIdProduct());

        ResultSet rs = ps.executeQuery();
        boolean exists = rs.next();

        ps.close();
        rs.close();

        return exists;
    }

    public void deleteProductFromUser(int userId, int productId) throws Exception {
        PreparedStatement ps = connection.prepareStatement("DELETE FROM product_user WHERE id_user = ? AND id_product = ?");

        ps.setInt(1, userId);
        ps.setInt(2, productId);

        ps.executeUpdate();
        ps.close();
    }

}
