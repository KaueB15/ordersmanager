package br.com.sovis.ordersmanager.model;

public class OrdersProduct {
    
    private int id;
    private int idOrder;
    private int idProduct;
    private double value;

    public int getId() { 
        return id; 
    }

    public void setId(int id) { 
        this.id = id; 
    }

    public int getidOrder() { 
        return idOrder; 
    }

    public void setidOrder(int idOrder) { 
        this.idOrder = idOrder; 
    }

    public int getidProduct() { 
        return idProduct; 
    }

    public void setidProduct(int idProduct) { 
        this.idProduct = idProduct; 
    }

    public double getValue() { 
        return value; 
    }

    public void setValue(double value) { 
        this.value = value; 
    }

}
