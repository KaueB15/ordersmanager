package br.com.sovis.ordersmanager.view;

import br.com.sovis.ordersmanager.controller.OrderController;
import br.com.sovis.ordersmanager.model.Orders;
import totalcross.ui.Button;
import totalcross.ui.Container;
import totalcross.ui.Label;
import totalcross.ui.ListBox;
import totalcross.ui.MainWindow;
import totalcross.ui.Toast;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.PressListener;
import totalcross.ui.gfx.Color;

public class ListOrderView extends Container {

    private Label mainLabel = new Label("Pedidos");
    private ListBox list;
    private Button backButton = new Button("Voltar");
    private OrderController orderController = new OrderController();

    @Override
    public void initUI() {

        add(mainLabel, CENTER, TOP + 20);

        list = new ListBox();
        loadOrders();
        add(list, LEFT + 20, AFTER + 10, FILL - 40, FILL - 80);


        backButton.setBackColor(Color.RED);
        backButton.setForeColor(Color.WHITE);
        add(backButton, CENTER, AFTER + 10);

        backButton.addPressListener(new PressListener() {
            public void controlPressed(ControlEvent e) {
                MainWindow.getMainWindow().swap(new HomeView());
            }
        });
    }

    private void loadOrders() {

        try {
            Orders[] orders = orderController.findAll();
            list.removeAll();

            for (int i = 0; i < orders.length; i++) {
                Orders o = orders[i];

                list.add(
                    "Pedido #" + o.getId() +
                    " | " + o.getStatus() +
                    " | Total: " + o.getTotalValue()
                );
            }

        } catch (Exception e) {
            Toast.show("Erro ao carregar Pedidos", 2000);
            System.err.println(e);
        }
    }

}   
