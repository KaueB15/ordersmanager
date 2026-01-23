package br.com.sovis.ordersmanager.controller;

import br.com.sovis.ordersmanager.dao.OrderDAO;
import br.com.sovis.ordersmanager.dao.OrderProductDAO;
import br.com.sovis.ordersmanager.model.Orders;
import br.com.sovis.ordersmanager.model.OrdersProduct;

public class OrderController {

     private OrderDAO orderDAO = new OrderDAO();
    private OrderProductDAO itemDAO = new OrderProductDAO();

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

}
