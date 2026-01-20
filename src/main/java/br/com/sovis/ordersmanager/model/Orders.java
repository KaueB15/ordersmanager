package br.com.sovis.ordersmanager.model;

public class Orders {

    private int id;
    private int customerId;
    private double totalValue;
    private String orderDate;
    private String status;

    public int getId() { 
        return id; 
    }

    public void setId(int id) { 
        this.id = id; 
    }

    public int getCustomerId() { 
        return customerId; 
    }

    public void setCustomerId(int customerId) { 
        this.customerId = customerId; 
    }

    public double getTotalValue() { 
        return totalValue; 
    }

    public void setTotalValue(double totalValue) { 
        this.totalValue = totalValue; 
    }

    public String getOrderDate() { 
        return orderDate; 
    }

    public void setOrderDate(String orderDate) { 
        this.orderDate = orderDate; 
    }

    public String getStatus() { 
        return status; 
    }

    public void setStatus(String status) { 
        this.status = status; 
    }

}
