package br.com.sovis.ordersmanager.controller;

import br.com.sovis.ordersmanager.dao.UserDAO;

public class UserController {

    private UserDAO userDAO;

    public UserController() {
        userDAO = new UserDAO();
    }

    public boolean login(String email, String password) {
        try {
            return userDAO.autenticate(email, password);
        }catch (Exception e) {
            return false;
        }
    }

}
