package br.com.sovis.ordersmanager.controller;

import br.com.sovis.ordersmanager.dao.UserDAO;
import br.com.sovis.ordersmanager.model.User;

public class UserController {

    private UserDAO userDAO;

    public UserController() {
        userDAO = new UserDAO();
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

}
