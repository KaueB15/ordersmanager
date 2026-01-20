package br.com.sovis.ordersmanager.model;

public class Customer {

    private int id;
    private String name;
    private String email;
    private String phone;
    private String createdAt;

    public int getId() { 
        return id; 
    }

    public void setId(int id) { 
        this.id = id; 
    }

    public String getName() { 
        return name;
    }

    public void setName(String name) { 
        this.name = name; 
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() { 
        return phone; 
    }

    public void setPhone(String phone) { 
        this.phone = phone; 
    }

    public String getCreatedAt() { 
        return createdAt; 
    }

    public void setCreatedAt(String createdAt) { 
        this.createdAt = createdAt; 
    }
    
}
