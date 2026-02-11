package br.com.sovis.ordersmanager.controller;

import br.com.sovis.ordersmanager.dao.OrderProductDAO;
import br.com.sovis.ordersmanager.dao.ProductDAO;
import br.com.sovis.ordersmanager.dao.UsersProductDAO;
import br.com.sovis.ordersmanager.model.Product;

public class ProductController {

    private ProductDAO productDAO;
    private UsersProductDAO usersProductDAO;
    private OrderProductDAO orderProductDAO;

    public ProductController() {
        this.productDAO = new ProductDAO();
        this.usersProductDAO = new UsersProductDAO();
        this.orderProductDAO = new OrderProductDAO();
    }

    public void createProduct(Product product) throws Exception {
        productDAO.insert(product);
    }

    public Product[] findAll() throws Exception {
        return productDAO.findAll();
    }

    public void removeProduct(int productId) throws Exception {
        productDAO.removeProduct(productId);
    }

    public Product[] findByUserId(int userId) throws Exception {
        return usersProductDAO.findByUserId(userId);
    }

    public boolean productAlreadyUsed(int productId) throws Exception {
        return productDAO.productAlreadyUsed(productId);
    }

    public void removeOneProductQuantity(int productId, int orderId) throws Exception {
        orderProductDAO.removeOneProductQuantity(productId, orderId);
    }

    public void updateProduct(Product product) throws Exception {
        productDAO.update(product);
    }
 
}
