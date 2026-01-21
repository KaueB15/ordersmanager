package br.com.sovis.ordersmanager.dao;

import br.com.sovis.ordersmanager.db.Database;
import br.com.sovis.ordersmanager.model.Customer;
import totalcross.sql.Connection;
import totalcross.sql.PreparedStatement;

public class CustomerDAO {

    private Connection connection;

    public CustomerDAO() {
        this.connection = Database.getConnection();
    }

    public void insert(Customer customer) throws Exception {

        PreparedStatement ps = connection.prepareStatement("INSERT INTO customer (name, email, phone, createdAt) VALUES (?, ?, ?, ?)");
        ps.setString(1, customer.getName());
        ps.setString(2, customer.getEmail());
        ps.setString(3, customer.getPhone());
        ps.setString(4, customer.getCreatedAt().toString());

        ps.executeUpdate();
        ps.close();        
    }

}
