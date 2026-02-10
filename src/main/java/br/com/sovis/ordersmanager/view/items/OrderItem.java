package br.com.sovis.ordersmanager.view.items;

import br.com.sovis.ordersmanager.dto.OrderLoadingDTO;
import totalcross.ui.Container;
import totalcross.ui.Label;
import totalcross.ui.ScrollContainer;
import totalcross.ui.gfx.Color;

public class OrderItem extends ScrollContainer {

    private Label orderNumber;
    private Label orderStatus;
    private Label customerName;
    private Label orderPrice;
    private Container row = new Container();

    private OrderLoadingDTO order;

    public OrderItem(OrderLoadingDTO order) {
        super(false);
        this.order = order;
        
    }

    @Override
    public void initUI() {

        setBackColor(Color.WHITE);

        orderNumber = new Label("Pedido #" + order.getId());
        orderNumber.setForeColor(Color.BLACK);
        add(orderNumber, LEFT + 10, TOP, FILL - 20, PREFERRED);

        customerName = new Label(order.getCustomerName());
        customerName.setForeColor(Color.BLACK);
        add(customerName, LEFT + 10, AFTER + 5, FILL - 20, PREFERRED);

        add(row, LEFT + 10, AFTER + 10, FILL, PREFERRED);

        orderPrice = new Label("R$ " + order.getTotalValue());
        orderPrice.setForeColor(Color.BLACK);
        orderStatus = new Label(order.getStatus());
        orderStatus.setForeColor(order.getStatus().equals("FECHADO") ? Color.RED : Color.GREEN);

        row.add(orderPrice, LEFT, CENTER);
        row.add(orderStatus, RIGHT - 20, CENTER);

    }

    public OrderLoadingDTO getOrder() {
        return order;
    }
}