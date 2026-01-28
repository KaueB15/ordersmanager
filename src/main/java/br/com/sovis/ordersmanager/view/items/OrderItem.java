package br.com.sovis.ordersmanager.view.items;

import br.com.sovis.ordersmanager.model.Orders;
import br.com.sovis.ordersmanager.view.OrderInfoView;
import totalcross.ui.Container;
import totalcross.ui.Label;
import totalcross.ui.MainWindow;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.PressListener;
import totalcross.ui.gfx.Color;

public class OrderItem extends Container {
    private Orders order;

    public OrderItem(Orders order) {
        this.order = order;
    }

    @Override
    public void initUI() {

        setBackColor(Color.WHITE);

        Label label = new Label(
            "Pedido #" + order.getId() +
            " | " + order.getStatus() +
            " | Total: " + order.getTotalValue()
        );

        label.setForeColor(Color.BLACK);

        add(label, LEFT + 10, CENTER);

        addPressListener(new PressListener() {
            @Override
            public void controlPressed(ControlEvent e) {
                MainWindow.getMainWindow().swap(
                    new OrderInfoView(order.getId(), order.getStatus().split(" ")[0])
                );
            }
        });
    }

    public Orders getOrder() {
        return order;
    }
}