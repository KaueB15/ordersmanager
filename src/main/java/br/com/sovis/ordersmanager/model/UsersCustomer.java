package br.com.sovis.ordersmanager.model;

public class UsersCustomer {
    
    private int id;
    private int idUser;
    private int idCustomer;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setidCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }
    
    public int getidCustomer() {
        return idCustomer;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdUser() {
        return idUser;
    }

}