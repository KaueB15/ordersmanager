package br.com.sovis.ordersmanager.dao;

import java.sql.SQLException;

import br.com.sovis.ordersmanager.db.Database;
import br.com.sovis.ordersmanager.model.OrdersProduct;
import br.com.sovis.ordersmanager.model.ProductItem;
import totalcross.sql.Connection;
import totalcross.sql.PreparedStatement;
import totalcross.sql.ResultSet;
import totalcross.util.Vector;

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
        ps.setDouble(3, item.getValue());
        ps.setInt(4, item.getQuantity());

        ps.executeUpdate();
        ps.close();
    }

    public ProductItem[] findByOrderId(int orderId) throws Exception {

        String sql =
            "SELECT po.id_product ,p.name, po.price, po.quantity, p.description " +
            "FROM product_order po " +
            "JOIN product p ON p.id = po.id_product " +
            "WHERE po.id_order = ?";

        PreparedStatement ps =
            Database.getConnection().prepareStatement(sql);

        ps.setInt(1, orderId);

        ResultSet rs = ps.executeQuery();

        Vector items = new Vector();

        while (rs.next()) {
            ProductItem item = new ProductItem();
            item.setItemId(rs.getInt("id_product"));
            item.setProductName(rs.getString("name"));
            item.setPrice(rs.getDouble("price"));
            item.setQuantity(rs.getInt("quantity"));
            item.setDescription(rs.getString("description"));

            items.addElement(item);
        }

        rs.close();
        ps.close();

        ProductItem[] result =
            new ProductItem[items.size()];

        items.copyInto(result);

        return result;
    }

    public void deleteProductFromOrder(int orderId, int productId) throws Exception {

        PreparedStatement ps = connection.prepareStatement("DELETE FROM product_order WHERE id_order = ? AND id_product = ?");

        ps.setInt(1, orderId);
        ps.setInt(2, productId);

        ps.executeUpdate();
        ps.close();

    }

    public void addProductToOrder(int orderId, int productId, double price, int quantity) throws Exception {

        PreparedStatement check = connection.prepareStatement(
            "SELECT quantity, price FROM product_order WHERE id_order = ? AND id_product = ?"
        );

        PreparedStatement checkOrderValue = connection.prepareStatement(
            "SELECT total_price FROM orders WHERE id = ?"
        );

        checkOrderValue.setInt(1, orderId);

        check.setInt(1, orderId);
        check.setInt(2, productId);

        ResultSet rs = check.executeQuery();
        ResultSet rsOrder = checkOrderValue.executeQuery();

        if (rs.next()) {
            
            int currentQty = rs.getInt("quantity");
            double currentPrice = rs.getDouble("price");
            double currentTotalOrderPrice = rsOrder.getDouble("total_price");

            PreparedStatement updateProductOrder = connection.prepareStatement(
                "UPDATE product_order SET quantity = ?, price = ? WHERE id_order = ? AND id_product = ?"
            );

            updateProductOrder.setInt(1, currentQty + quantity);
            updateProductOrder.setDouble(2, currentPrice + price);
            updateProductOrder.setInt(3, orderId);
            updateProductOrder.setInt(4, productId);
            
            PreparedStatement updateOrder = connection.prepareStatement(
                "UPDATE orders SET total_price = ? WHERE id = ?"
            );
            
            updateOrder.setDouble(1, currentTotalOrderPrice + price);
            updateOrder.setInt(2, orderId);

            updateProductOrder.executeUpdate();
            updateProductOrder.close();
            updateOrder.executeUpdate();
            updateOrder.close();

        } else {

            double currentTotalOrderPrice = rsOrder.getDouble("total_price");

            PreparedStatement insert = connection.prepareStatement(
                "INSERT INTO product_order (id_order, id_product, price, quantity) VALUES (?, ?, ?, ?)"
            );

            insert.setInt(1, orderId);
            insert.setInt(2, productId);
            insert.setDouble(3, price);
            insert.setInt(4, quantity);

            PreparedStatement updateOrder = connection.prepareStatement(
                "UPDATE orders SET total_price = ? WHERE id = ?"
            );
            
            updateOrder.setDouble(1, currentTotalOrderPrice + price);
            updateOrder.setInt(2, orderId);

            updateOrder.executeUpdate();
            updateOrder.close();

            insert.executeUpdate();
            insert.close();
        }

        rs.close();
        check.close();
    }

}

