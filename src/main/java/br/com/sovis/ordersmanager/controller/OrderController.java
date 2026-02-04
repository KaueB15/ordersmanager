package br.com.sovis.ordersmanager.controller;

import br.com.sovis.ordersmanager.dao.OrderDAO;
import br.com.sovis.ordersmanager.dao.OrderProductDAO;
import br.com.sovis.ordersmanager.model.Orders;
import br.com.sovis.ordersmanager.model.OrdersProduct;
import br.com.sovis.ordersmanager.model.ProductItem;

public class OrderController {

    private OrderDAO orderDAO;
    private OrderProductDAO itemDAO;

    public OrderController() {
        orderDAO = new OrderDAO();
        itemDAO = new OrderProductDAO();
    }

    public void createOrder(Orders order, OrdersProduct[] items) throws Exception {

        int orderId = orderDAO.insert(order);

        double total = 0;

        for (int i = 0; i < items.length; i++) {
            items[i].setidOrder(orderId);
            itemDAO.insert(items[i]);

            total += items[i].getQuantity() * items[i].getValue();
        }

        orderDAO.updateTotal(orderId, total);
    }

    public Orders[] findAll() throws Exception {
        return orderDAO.findAll();
    }

    public void cancelOrder(int orderId) throws Exception {
        orderDAO.cancelOrder(orderId);
    }

    public void closeOrder(int orderId) throws Exception {
        orderDAO.closeOrder(orderId);
    }

    public ProductItem[] getProductsFromOrder(int orderId) throws Exception {

        return itemDAO.findByOrderId(orderId);

    }

    public void deleteProductFromOrder(int orderId, int productId) throws Exception {

        itemDAO.deleteProductFromOrder(orderId, productId);

    }

    public void addProductToOrder(int orderId, int productId, double price, int quantity) throws Exception {
        itemDAO.addProductToOrder(orderId, productId, price, quantity);
    }

}
