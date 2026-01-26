package br.com.sovis.ordersmanager.dao;

import java.sql.SQLException;

import br.com.sovis.ordersmanager.db.Database;
import br.com.sovis.ordersmanager.model.Orders;
import totalcross.sql.Connection;
import totalcross.sql.PreparedStatement;
import totalcross.sql.ResultSet;
import totalcross.sql.Statement;
import totalcross.util.Vector;

public class OrderDAO {

    private Connection connection;

    public OrderDAO() {
        try {
            this.connection = Database.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int insert(Orders order) throws Exception {

        String sql =
            "INSERT INTO orders (id_customer, total_price, order_date, status) " +
            "VALUES (?, ?, ?, ?)";

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, order.getCustomerId());
        ps.setDouble(2, 0);
        ps.setString(3, order.getOrderDate());
        ps.setString(4, order.getStatus());
        ps.executeUpdate();
        ps.close();
        
        Statement st = Database.getConnection().createStatement();
        ResultSet rs = st.executeQuery("SELECT last_insert_rowid()");

        rs.next();
        int id = rs.getInt(1);

        rs.close();
        st.close();

        return id;
    }

    public void updateTotal(int orderId, double total) throws Exception {

        PreparedStatement ps = connection.prepareStatement(
            "UPDATE orders SET total_price = ? WHERE id = ?"
        );

        ps.setDouble(1, total);
        ps.setInt(2, orderId);

        ps.executeUpdate();
        ps.close();
    }

    public void closeOrder(int orderId) throws Exception {

        PreparedStatement ps = connection.prepareStatement(
            "UPDATE orders SET status = ? WHERE id = ?"
        );

        ps.setString(1, "FECHADO");
        ps.setInt(2, orderId);

        ps.executeUpdate();
        ps.close();

    }

    public void cancelOrder(int orderId) throws Exception {

        PreparedStatement ps = connection.prepareStatement("DELETE FROM orders WHERE id = ?");

        ps.setInt(1, orderId);

        ps.executeUpdate();
        ps.close();

    }

    public Orders[] findAll() throws Exception {

        String sql =
            "SELECT o.id, o.order_date, o.total_price, o.status, c.name AS customer_name " +
            "FROM orders o " +
            "JOIN customer c ON c.id = o.id_customer " +
            "ORDER BY o.id DESC";

        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(sql);

        Vector list = new Vector();

        while (rs.next()) {

            Orders order = new Orders();
            order.setId(rs.getInt("id"));
            order.setOrderDate(rs.getString("order_date"));
            order.setTotalValue(rs.getDouble("total_price"));
            order.setStatus(rs.getString("status"));
            order.setStatus(order.getStatus() + " - " + rs.getString("customer_name"));
            list.addElement(order);
        }

        rs.close();
        st.close();

        Orders[] orders = new Orders[list.size()];
        list.copyInto(orders);

        return orders;
    }

}
