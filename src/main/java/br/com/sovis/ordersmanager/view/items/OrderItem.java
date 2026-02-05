package br.com.sovis.ordersmanager.view.items;

import br.com.sovis.ordersmanager.dto.OrderLoadingDTO;
import totalcross.ui.Container;
import totalcross.ui.Label;
import totalcross.ui.gfx.Color;

public class OrderItem extends Container {
    private OrderLoadingDTO order;

    public OrderItem(OrderLoadingDTO order) {
        this.order = order;
    }

    @Override
    public void initUI() {

        setBackColor(Color.WHITE);

        Label label = new Label(
            "Pedido #" + order.getId() +
            " | " + order.getStatus() + " - " + 
            order.getCustomerName() +
            " | Total: " + order.getTotalValue()
        );

        label.setForeColor(Color.BLACK);

        add(label, LEFT + 10, CENTER);
    }

    public OrderLoadingDTO getOrder() {
        return order;
    }
}