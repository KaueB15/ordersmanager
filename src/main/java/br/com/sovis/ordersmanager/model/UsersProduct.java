package br.com.sovis.ordersmanager.model;

public class UsersProduct {
    
    private int id;
    private int idUser;
    private int idProduct;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }
    
    public int getIdProduct() {
        return idProduct;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdUser() {
        return idUser;
    }

}
