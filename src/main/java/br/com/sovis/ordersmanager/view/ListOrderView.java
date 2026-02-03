package br.com.sovis.ordersmanager.view;

import br.com.sovis.ordersmanager.controller.OrderController;
import br.com.sovis.ordersmanager.model.Orders;
import br.com.sovis.ordersmanager.model.User;
import br.com.sovis.ordersmanager.view.items.OrderItem;
import totalcross.ui.Button;
import totalcross.ui.Container;
import totalcross.ui.Label;
import totalcross.ui.ListContainer;
import totalcross.ui.MainWindow;
import totalcross.ui.Toast;
import totalcross.ui.gfx.Color;

public class ListOrderView extends Container {

    private Label mainLabel = new Label("Pedidos");
    private ListContainer list;
    private Container buttonRows = new Container();
    private Container buttonRowsBottom = new Container();

    private Button backButton = new Button("Voltar");
    private Button cancelOrderButton = new Button("Cancelar");
    private Button closeOrderButton = new Button("Fechar");
    private Button infoButton = new Button("Produtos");

    private OrderController orderController = new OrderController();
    private Orders[] orders;
    private User user;

    public ListOrderView(User user) {
        this.user = user;
    }

    @Override
    public void initUI() {

        setBackColor(Color.getRGB(242, 247, 244));

        mainLabel.setForeColor(Color.getRGB(46, 204, 113));
        mainLabel.setFont(mainLabel.getFont().adjustedBy(6));
        add(mainLabel, CENTER, TOP + 20);


        list = new ListContainer();
        add(list, LEFT + 20, AFTER + 10, FILL - 40, FILL - 200);
        loadOrders();

        buttonRows.setBackColor(Color.WHITE);
        add(buttonRows, LEFT, BOTTOM - 50, FILL, 70);
        buttonRowsBottom.setBackColor(Color.WHITE);
        add(buttonRowsBottom, LEFT, BOTTOM, FILL, 70);

        cancelOrderButton.setBackColor(Color.getRGB(244, 67, 54));
        cancelOrderButton.setForeColor(Color.WHITE);
        buttonRows.add(cancelOrderButton, LEFT + 10, TOP + 8, (buttonRows.getWidth() / 2) - 15, 45);

        closeOrderButton.setBackColor(Color.getRGB(46, 204, 113));
        closeOrderButton.setForeColor(Color.WHITE);
        buttonRows.add(closeOrderButton, AFTER + 10, SAME, (buttonRows.getWidth() / 2) - 15, 45);

        infoButton.setBackColor(Color.getRGB(156, 39, 176));
        infoButton.setForeColor(Color.WHITE);
        buttonRowsBottom.add(infoButton, AFTER + 10, SAME, (buttonRows.getWidth() / 2) - 15, 45);

        backButton.setBackColor(Color.getRGB(96, 125, 139));
        backButton.setForeColor(Color.WHITE);
        buttonRowsBottom.add(backButton, AFTER + 10, SAME, (buttonRows.getWidth() / 2) - 15, 45);

        Button addButton = new Button("+");
        addButton.setBackColor(Color.getRGB(46, 204, 113));
        addButton.setForeColor(Color.WHITE);
        addButton.setFont(addButton.getFont().adjustedBy(10));
        add(addButton, RIGHT - 20, BOTTOM - 120, 60, 60);

        backButton.addPressListener(e ->
            MainWindow.getMainWindow().swap(new HomeView(user))
        );

        cancelOrderButton.addPressListener(e ->
            cancelOrder()
        );

        closeOrderButton.addPressListener(e ->
            closeOrder()
        );

        infoButton.addPressListener(e -> {
            int selected = list.getSelectedIndex();
            if (selected == -1) {
                Toast.show("Selecione um pedido", 2000);
                return;
            }
            MainWindow.getMainWindow().swap(
                new OrderInfoView(
                    orders[selected].getId(),
                    orders[selected].getStatus().split(" ")[0],
                    user
                )
            );
        });

        addButton.addPressListener(e ->
            MainWindow.getMainWindow().swap(new OrderView(user))
        );
    }

    private void loadOrders() {
        try {
            orders = orderController.findAll();
            list.removeAll();
            for (int i = 0; i < orders.length; i++) {
                list.addContainer(new OrderItem(orders[i]));
            }
        } catch (Exception e) {
            Toast.show("Erro ao carregar pedidos", 2000);
        }
    }

    private void cancelOrder() {
        int selected = list.getSelectedIndex();
        if (selected == -1) {
            Toast.show("Selecione um pedido", 2000);
            return;
        }
        if (orders[selected].getStatus().startsWith("FECHADO")) {
            Toast.show("Pedido já fechado", 2000);
            return;
        }
        try {
            orderController.cancelOrder(orders[selected].getId());
            MainWindow.getMainWindow().swap(new ListOrderView(user));
        } catch (Exception e) {
            Toast.show("Falha ao cancelar pedido", 2000);
        }
    }

    private void closeOrder() {
        int selected = list.getSelectedIndex();
        if (selected == -1) {
            Toast.show("Selecione um pedido", 2000);
            return;
        }
        try {
            orderController.closeOrder(orders[selected].getId());
            MainWindow.getMainWindow().swap(new ListOrderView(user));
        } catch (Exception e) {
            Toast.show("Falha ao fechar pedido", 2000);
        }
    }
}
