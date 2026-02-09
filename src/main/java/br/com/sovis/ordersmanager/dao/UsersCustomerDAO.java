package br.com.sovis.ordersmanager.dao;

import br.com.sovis.ordersmanager.db.Database;
import br.com.sovis.ordersmanager.model.Customer;
import br.com.sovis.ordersmanager.model.UsersCustomer;
import totalcross.sql.Connection;
import totalcross.sql.PreparedStatement;
import totalcross.sql.ResultSet;
import totalcross.util.Vector;

public class UsersCustomerDAO {

    private Connection connection;

    public UsersCustomerDAO() {
        try {
            this.connection = Database.getConnection();
        } catch(Exception e) {
            e.printStackTrace();
        } 
    }

    public void insert(UsersCustomer item) throws Exception {

        PreparedStatement ps = connection.prepareStatement(
            "INSERT INTO customer_user (id_user, id_customer) VALUES (?, ?)"
        );

        ps.setInt(1, item.getIdUser());
        ps.setInt(2, item.getidCustomer());

        ps.executeUpdate();
        ps.close();
    }

    public Customer[] findByUserId(int userId) throws Exception {
        String sql =
            "SELECT DISTINCT cu.id_customer, c.name " +
            "FROM customer_user cu " +
            "JOIN customer c ON c.id = cu.id_customer " +
            "WHERE cu.id_user = ?";

        PreparedStatement ps = Database.getConnection().prepareStatement(sql);

        ps.setInt(1, userId);

        ResultSet rs = ps.executeQuery();

        Vector items = new Vector();

        while (rs.next()) {
            Customer item = new Customer();
            item.setId(rs.getInt("id_customer"));
            item.setName(rs.getString("name"));

            items.addElement(item);
        }

        rs.close();
        ps.close();

        Customer[] result =
            new Customer[items.size()];

        items.copyInto(result);

        return result;
    }

    public boolean customerAlreadyAssocieated(UsersCustomer item) throws Exception {
        PreparedStatement ps = connection.prepareStatement(
            "SELECT * FROM customer_user WHERE id_user = ? AND id_customer = ?"
        );

        ps.setInt(1, item.getIdUser());
        ps.setInt(2, item.getidCustomer());

        ResultSet rs = ps.executeQuery();
        boolean exists = rs.next();

        ps.close();
        rs.close();

        return exists;
    }

    public void deleteCustomerFromUser(int userId, int customerId) throws Exception {
        PreparedStatement ps = connection.prepareStatement("DELETE FROM customer_user WHERE id_user = ? AND id_customer = ?");

        ps.setInt(1, userId);
        ps.setInt(2, customerId);

        ps.executeUpdate();
        ps.close();
    }

}
