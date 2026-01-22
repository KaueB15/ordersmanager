package br.com.sovis.ordersmanager.controller;

import br.com.sovis.ordersmanager.dao.ProductDAO;
import br.com.sovis.ordersmanager.model.Product;

public class ProductController {

    private ProductDAO productDAO;

    public ProductController() {
        this.productDAO = new ProductDAO();
    }

    public void createProduct(String name, String description, double price) throws Exception {

        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        
        productDAO.insert(product);

    }

    public Product[] findAll() throws Exception {
        return productDAO.findAll();
    }

}
