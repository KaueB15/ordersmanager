package br.com.sovis.ordersmanager.view;

import br.com.sovis.ordersmanager.controller.OrderController;
import br.com.sovis.ordersmanager.model.Orders;
import br.com.sovis.ordersmanager.view.items.OrderItem;
import totalcross.ui.Button;
import totalcross.ui.Container;
import totalcross.ui.Label;
import totalcross.ui.ListContainer;
import totalcross.ui.MainWindow;
import totalcross.ui.Toast;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.PressListener;
import totalcross.ui.gfx.Color;

public class ListOrderView extends Container {

    private Label mainLabel = new Label("Pedidos");
    private ListContainer list;
    private Container buttonRows = new Container();
    private Container buttonRowsInfos = new Container();
    private Button backButton = new Button("Voltar");
    private Button cancelOrderButton = new Button("Cancelar");
    private Button closeOrderButton = new Button("Fechar Pedido");
    private Button getInfoButton = new Button("Produtos");
    private OrderController orderController = new OrderController();

    private Orders[] orders;

    @Override
    public void initUI() {

        add(mainLabel, CENTER, TOP + 20);

        list = new ListContainer();
        
        add(list, LEFT + 20, AFTER + 10, FILL - 40, FILL - 150);
        loadOrders();

        add(buttonRows, LEFT + 20, AFTER + 10, FILL - 40, 45);

        closeOrderButton.setBackColor(Color.MAGENTA);
        closeOrderButton.setForeColor(Color.WHITE);
        buttonRows.add(closeOrderButton, LEFT, TOP, (buttonRows.getWidth() / 2) -5, PREFERRED);
        cancelOrderButton.setBackColor(Color.MAGENTA);
        cancelOrderButton.setForeColor(Color.WHITE);
        buttonRows.add(cancelOrderButton, RIGHT, TOP, (buttonRows.getWidth() / 2) -5, PREFERRED);

        add(buttonRowsInfos, LEFT + 20, AFTER + 10, FILL - 40, 45);

        getInfoButton.setBackColor(Color.MAGENTA);
        getInfoButton.setForeColor(Color.WHITE);
        buttonRowsInfos.add(getInfoButton, LEFT, TOP, (buttonRowsInfos.getWidth() / 2) -5, PREFERRED);
        backButton.setBackColor(Color.RED);
        backButton.setForeColor(Color.WHITE);
        buttonRowsInfos.add(backButton, RIGHT, TOP, (buttonRowsInfos.getWidth() / 2) -5, PREFERRED);

        backButton.addPressListener(new PressListener() {
            public void controlPressed(ControlEvent e) {
                MainWindow.getMainWindow().swap(new HomeView());
            }
        });

        cancelOrderButton.addPressListener(new PressListener() {
            public void controlPressed(ControlEvent e) {
                cancelOrder();
            }
        });

        closeOrderButton.addPressListener(new PressListener() {
            public void controlPressed(ControlEvent e) {
                closeOrder();
            }
        });

        getInfoButton.addPressListener(new PressListener() {
            public void controlPressed(ControlEvent e) {
                int orderSelected = list.getSelectedIndex();

                if(orderSelected == -1) {
                    Toast.show("Selecione um pedido", 2000);
                }
                
                MainWindow.getMainWindow().swap(new OrderInfoView(orders[orderSelected].getId(), orders[orderSelected].getStatus().split(" ")[0]));
            }
        });

    }

    private void loadOrders() {

        try {
            orders = orderController.findAll();
            list.removeAll();

            for (int i = 0; i < orders.length; i++) {
                OrderItem item = new OrderItem(orders[i]);

                if (i == 0) {
                    list.addContainer(item);
                } else {
                    list.addContainer(item);
                }

            }

        } catch (Exception e) {
            Toast.show("Erro ao carregar Pedidos", 2000);
            System.err.println(e);
        }

    }

    private void cancelOrder() {

        int orderSelected = list.getSelectedIndex();

        if(orderSelected == -1) {
            Toast.show("Selecione um pedido", 2000);
        }

        if(orders[orderSelected].getStatus().split(" ")[0].equals("FECHADO")) {
            Toast.show("Pedido já fechado!", 2000);
            return;
        }

        try {

            int orderId = orders[orderSelected].getId();
            orderController.cancelOrder(orderId);
            loadOrders();

        } catch (Exception e) {
            
            Toast.show("Falha ao cancelar pedido", 2000);
            
        }

    }

    private void closeOrder() {

        int orderSelected = list.getSelectedIndex();

        if(orderSelected == -1) {
            Toast.show("Selecione um pedido", 2000);
        }

        try {

            int orderId = orders[orderSelected].getId();
            orderController.closeOrder(orderId);
            loadOrders();

        } catch (Exception e) {
            
            Toast.show("Falha ao cancelar pedido", 2000);
            
        }
        
    }

}   
