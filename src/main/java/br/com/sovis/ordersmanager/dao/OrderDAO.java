package br.com.sovis.ordersmanager.dao;

import java.sql.SQLException;

import br.com.sovis.ordersmanager.db.Database;
import br.com.sovis.ordersmanager.model.Orders;
import totalcross.sql.Connection;
import totalcross.sql.PreparedStatement;
import totalcross.sql.ResultSet;
import totalcross.sql.Statement;

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

        Connection conn = Database.getConnection();

        PreparedStatement ps = conn.prepareStatement(
            "UPDATE orders SET total_price = ? WHERE id = ?"
        );

        ps.setDouble(1, total);
        ps.setInt(2, orderId);

        ps.executeUpdate();
        ps.close();
    }

}
