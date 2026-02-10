package br.com.sovis.ordersmanager.view.list;

import br.com.sovis.ordersmanager.controller.OrderController;
import br.com.sovis.ordersmanager.dto.OrderLoadingDTO;
import br.com.sovis.ordersmanager.model.User;
import br.com.sovis.ordersmanager.view.HomeView;
import br.com.sovis.ordersmanager.view.forms.OrderView;
import br.com.sovis.ordersmanager.view.items.OrderItem;
import totalcross.ui.Button;
import totalcross.ui.Container;
import totalcross.ui.Edit;
import totalcross.ui.Label;
import totalcross.ui.ListContainer;
import totalcross.ui.MainWindow;
import totalcross.ui.Toast;
import totalcross.ui.gfx.Color;
import totalcross.ui.image.Image;

public class ListOrderView extends Container {

    private Label mainLabel = new Label("Pedidos");
    private ListContainer list;
    private Container filterRow = new Container();
    private Edit filterEdit = new Edit();

    private Button filterButton;
    private Button addButton;
    private Button backButton;
    private Button cancelOrderButton;
    private Button closeOrderButton;
    private Button infoButton;

    private OrderController orderController = new OrderController();
    private OrderLoadingDTO[] orders;
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

        add(filterRow, LEFT+20, AFTER + 10, FILL - 40, 60);
        filterRow.add(filterEdit, LEFT, TOP, (filterRow.getWidth() * 70) / 100, FILL);
        try {
            filterButton = new Button(new Image("search.png").getScaledInstance(20, 20));
            filterButton.setBackColor(Color.getRGB(156, 39, 176));
            filterButton.setForeColor(Color.WHITE);
            filterRow.add(filterButton, AFTER + 5, SAME, FILL, FILL);
        } catch(Exception e) {
            System.out.println(e);
        }

        list = new ListContainer();
        add(list, LEFT + 20, AFTER + 10, FILL - 40, FILL - 100);
        loadOrders();

        try {

            cancelOrderButton = new Button(new Image("delete.png").getScaledInstance(20, 20));
            cancelOrderButton.setBackColor(Color.getRGB(156, 39, 176));
            cancelOrderButton.setForeColor(Color.WHITE);
            add(cancelOrderButton, RIGHT - 250, BOTTOM - 10, 60, 60);

            infoButton = new Button(new Image("products.png").getScaledInstance(20, 20));
            infoButton.setBackColor(Color.getRGB(46, 204, 113));
            infoButton.setForeColor(Color.WHITE);
            infoButton.setFont(infoButton.getFont().adjustedBy(10));
            add(infoButton, RIGHT - 190, BOTTOM - 10, 60, 60);
            
            closeOrderButton = new Button(new Image("close.png").getScaledInstance(20, 20));
            closeOrderButton.setBackColor(Color.getRGB(46, 204, 113));
            closeOrderButton.setForeColor(Color.WHITE);
            closeOrderButton.setFont(closeOrderButton.getFont().adjustedBy(10));
            add(closeOrderButton, RIGHT - 130, BOTTOM - 10, 60, 60);

            addButton = new Button("+");
            addButton.setBackColor(Color.getRGB(46, 204, 113));
            addButton.setForeColor(Color.WHITE);
            addButton.setFont(addButton.getFont().adjustedBy(10));
            add(addButton, RIGHT - 70, BOTTOM - 10, 60, 60);

            backButton = new Button(new Image("home.png").getScaledInstance(20, 20));
            backButton.setBackColor(Color.getRGB(244, 67, 54));
            backButton.setForeColor(Color.WHITE);
            add(backButton, RIGHT - 10, BOTTOM - 10, 60, 60);
        } catch(Exception e) {
            System.out.println(e);
        }

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

        filterButton.addPressListener(e -> {
            applyFilter();
        });
    }

    private void loadOrders() {
        try {
            orders = orderController.findAll();
            list.removeAll();
            for (int i = 0; i < orders.length; i++) {
                if(user.getAdmin() == 1 ){
                    list.addContainer(new OrderItem(orders[i]));
                } else if (orders[i].getUserId() == user.getId()) {
                    list.addContainer(new OrderItem(orders[i]));
                }
            }
        } catch (Exception e) {
            Toast.show("Erro ao carregar pedidos", 2000);
            System.err.println(e);
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
    
    private void applyFilter() {
        String filter = filterEdit.getText().trim().toLowerCase();

        list.removeAllContainers();
        
        for(int i = 0; i < orders.length; i++) {
            OrderLoadingDTO order = orders[i];

            if(filter.length() == 0 || order.getCustomerName().toLowerCase().contains(filter)) {
                if(user.getAdmin() == 1 ){
                    list.addContainer(new OrderItem(orders[i]));
                } else if (orders[i].getUserId() == user.getId()) {
                    list.addContainer(new OrderItem(orders[i]));
                }
            }
        }

    }
}
