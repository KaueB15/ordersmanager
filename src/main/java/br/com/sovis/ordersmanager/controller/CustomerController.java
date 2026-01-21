package br.com.sovis.ordersmanager.controller;

import br.com.sovis.ordersmanager.dao.CustomerDAO;
import br.com.sovis.ordersmanager.model.Customer;
import totalcross.sys.Time;

public class CustomerController {

    private CustomerDAO customerDAO;

    public CustomerController() {
        this.customerDAO = new CustomerDAO();
    }

    public void createCustomer(String name, String email, String phone) throws Exception {

        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        customer.setPhone(phone);
        customer.setCreatedAt(new Time().toString());
        
        customerDAO.insert(customer);

    }

}
