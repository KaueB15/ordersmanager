package br.com.sovis.ordersmanager.dao;

import java.sql.SQLException;

import br.com.sovis.ordersmanager.db.Database;
import br.com.sovis.ordersmanager.model.Customer;
import totalcross.sql.Connection;
import totalcross.sql.PreparedStatement;
import totalcross.sql.ResultSet;
import totalcross.util.Vector;

public class CustomerDAO {

    private Connection connection;

    public CustomerDAO() {
        try {
            this.connection = Database.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    public Customer[] findAll() throws Exception {

        PreparedStatement ps = connection.prepareStatement("SELECT * FROM customer");
        ResultSet rs = ps.executeQuery();
        
        Vector vector = new Vector();

        while(rs.next()) {
            Customer customer = new Customer();

            customer.setId(rs.getInt("id"));
            customer.setName(rs.getString("name"));
            customer.setEmail(rs.getString("email"));
            customer.setPhone(rs.getString("phone"));
            vector.addElement(customer);
        }

        Customer[] customers = new Customer[vector.size()];
        vector.copyInto(customers);

        return customers;

    }

    public String[] getCustomersNames() throws Exception {

        Customer[] customers = findAll();
        String[] customersNames = new String[customers.length + 1];
        customersNames[0] = "Selecione o Cliente";

        for(int i = 0; i < customers.length; i++) {
            customersNames[i+1] = customers[i].getName();
        }

        return customersNames;

    }

    public void removeCustomer(int customerId) throws Exception {

        PreparedStatement ps = connection.prepareStatement("DELETE FROM customer WHERE id = ?");

        ps.setInt(1, customerId);

        ps.executeUpdate();
        ps.close();

    }

}
