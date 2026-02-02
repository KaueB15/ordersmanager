package br.com.sovis.ordersmanager.model;

public class User {

    private int id;
    private String email;
    private String password;
    private int admin;

    public User() {}

    public User(String email, String password, int admin) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }

    public int getAdmin() {
        return admin;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }
    
}
