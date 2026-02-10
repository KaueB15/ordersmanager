package br.com.sovis.ordersmanager.view.list;

import br.com.sovis.ordersmanager.controller.OrderController;
import br.com.sovis.ordersmanager.model.ProductItem;
import br.com.sovis.ordersmanager.model.User;
import br.com.sovis.ordersmanager.view.forms.OrderView;
import br.com.sovis.ordersmanager.view.items.OrderProductItem;
import totalcross.ui.Button;
import totalcross.ui.Container;
import totalcross.ui.Label;
import totalcross.ui.ListContainer;
import totalcross.ui.MainWindow;
import totalcross.ui.Toast;
import totalcross.ui.gfx.Color;
import totalcross.ui.image.Image;

public class OrderInfoView extends Container {

    private int orderId;
    private String status;

    private Label mainLabel = new Label("Produtos do Pedido");
    private ListContainer list;
    private Button backButton;
    private Button cancelProductButton;
    private Button addProductButton = new Button("+");

    private OrderController orderController = new OrderController();
    private ProductItem[] productItems;
    private User user;

    public OrderInfoView(int orderId, String status, User user) {
        this.orderId = orderId;
        this.status = status;
        this.user = user;
    }

    @Override
    public void initUI() {

        setBackColor(Color.getRGB(240, 248, 243));

        mainLabel.setForeColor(Color.getRGB(39, 174, 96));
        add(mainLabel, CENTER, TOP + 20);

        list = new ListContainer();
        add(list, LEFT + 20, AFTER + 15, FILL - 40, FILL - 80);
        loadProducts();

        
        try {
            cancelProductButton = new Button(new Image("delete.png").getScaledInstance(20, 20));
            cancelProductButton.setBackColor(Color.getRGB(156, 39, 176));
            cancelProductButton.setForeColor(Color.WHITE);
            add(cancelProductButton, RIGHT - 130, BOTTOM - 10, 60, 60);

            addProductButton.setBackColor(Color.getRGB(39, 174, 96));
            addProductButton.setForeColor(Color.WHITE);
            addProductButton.setFont(addProductButton.getFont().adjustedBy(10));
            add(addProductButton, RIGHT - 70, BOTTOM - 10, 60, 60);
    
            backButton = new Button(new Image("home.png").getScaledInstance(20, 20));
            backButton.setBackColor(Color.getRGB(244, 67, 54));
            backButton.setForeColor(Color.WHITE);
            add(backButton, RIGHT - 10, BOTTOM - 10, 60, 60);
        } catch(Exception e) {
            System.out.println(e);
        }


        backButton.addPressListener(e ->
            MainWindow.getMainWindow().swap(new ListOrderView(user))
        );

        cancelProductButton.addPressListener(e ->
            deleteProduct()
        );

        addProductButton.addPressListener(e -> {

            if (status.equals("FECHADO")) {
                Toast.show("Pedido já fechado!", 2000);
                return;
            }

            MainWindow.getMainWindow().swap(new OrderView(orderId, user));
        });
    }

    private void loadProducts() {

        try {
            productItems = orderController.getProductsFromOrder(orderId);
            list.removeAll();

            for (int i = 0; i < productItems.length; i++) {
                list.addContainer(new OrderProductItem(productItems[i]));
            }

        } catch (Exception e) {
            Toast.show("Erro ao carregar produtos", 2000);
        }
    }

    private void deleteProduct() {

        int selected = list.getSelectedIndex();

        if (selected == -1) {
            Toast.show("Selecione um produto", 2000);
            return;
        }

        if (status.equals("FECHADO")) {
            Toast.show("Pedido já fechado!", 2000);
            return;
        }

        try {
            int productId = productItems[selected].getItemId();
            orderController.deleteProductFromOrder(orderId, productId);
            MainWindow.getMainWindow().swap(new OrderInfoView(orderId, status, user));
        } catch (Exception e) {
            Toast.show("Falha ao remover produto", 2000);
        }
    }
}
