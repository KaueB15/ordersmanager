package br.com.sovis.ordersmanager.controller;

import br.com.sovis.ordersmanager.dao.UserDAO;
import br.com.sovis.ordersmanager.dao.UsersCustomerDAO;
import br.com.sovis.ordersmanager.dao.UsersProductDAO;
import br.com.sovis.ordersmanager.model.User;
import br.com.sovis.ordersmanager.model.UsersCustomer;
import br.com.sovis.ordersmanager.model.UsersProduct;

public class UserController {

    private UserDAO userDAO;
    private UsersProductDAO usersProductDAO;
    private UsersCustomerDAO usersCustomerDAO;

    public UserController() {
        userDAO = new UserDAO();
        usersProductDAO = new UsersProductDAO();
        usersCustomerDAO = new UsersCustomerDAO();
    }

    public User login(String email, String password) {
        try {
            return userDAO.autenticate(email, password);
        }catch (Exception e) {
            return null;
        }
    }

    public void createUser(String email, String password) {
        try {
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            userDAO.insert(user);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public User[] findAll() throws Exception {
        return userDAO.findAll();
    }

    public void createUserProductAssociation(UsersProduct[] item) throws Exception {
        for(int i = 0; i < item.length; i++) {
            usersProductDAO.insert(item[i]);
        }
    }

    public boolean productAlreadyAssocieated(UsersProduct item) throws Exception {
        return usersProductDAO.productAlreadyAssocieated(item);
    }  

    public void removeUser(int userId) throws Exception {
        userDAO.removeUser(userId);
    }

    public void deleteProductFromUser(int userId, int productId) throws Exception {
        usersProductDAO.deleteProductFromUser(userId, productId);
    }

    public void createUserCustomerAssociation(UsersCustomer[] item) throws Exception {
        for(int i = 0; i < item.length; i++) {
            usersCustomerDAO.insert(item[i]);
        }
    }

    public boolean customerAlreadyAssocieated(UsersCustomer item) throws Exception {
        return usersCustomerDAO.customerAlreadyAssocieated(item);
    }

    public void deleteCustomerFromUser(int userId, int customerId) throws Exception {
        usersCustomerDAO.deleteCustomerFromUser(userId, customerId);
    }

}
