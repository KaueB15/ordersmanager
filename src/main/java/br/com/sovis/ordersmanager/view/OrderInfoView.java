package br.com.sovis.ordersmanager.view;

import br.com.sovis.ordersmanager.controller.OrderController;
import br.com.sovis.ordersmanager.model.ProductItem;
import totalcross.ui.Button;
import totalcross.ui.Container;
import totalcross.ui.Label;
import totalcross.ui.ListBox;
import totalcross.ui.MainWindow;
import totalcross.ui.Toast;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.PressListener;
import totalcross.ui.gfx.Color;

public class OrderInfoView extends Container {

    private int orderId;
    private Label mainLabel = new Label("Produtos do Pedido");
    private ListBox list;
    private Container buttonRows = new Container();
    private Button backButton = new Button("Voltar");
    private Button cancelProductButton = new Button("Retirar Produto");
    private OrderController orderController = new OrderController();

    private ProductItem[] productItems; 

    public OrderInfoView(int orderId) {
        this.orderId = orderId;
    }
    
    public void initUI() {

        add(mainLabel, CENTER, TOP + 20);

        list = new ListBox();
        loadProducts();
        add(list, LEFT + 20, AFTER + 10, FILL - 40, FILL - 150);

        add(buttonRows, LEFT + 20, AFTER + 10, FILL - 40, FILL - 80);

        cancelProductButton.setBackColor(Color.MAGENTA);
        cancelProductButton.setForeColor(Color.WHITE);
        buttonRows.add(cancelProductButton, LEFT, TOP, (buttonRows.getWidth() / 2) -5, PREFERRED);
        backButton.setBackColor(Color.RED);
        backButton.setForeColor(Color.WHITE);
        buttonRows.add(backButton, RIGHT, TOP, (buttonRows.getWidth() / 2) -5, PREFERRED);

        backButton.addPressListener(new PressListener() {
            public void controlPressed(ControlEvent e) {
                MainWindow.getMainWindow().swap(new ListOrderView());
            }
        });

        cancelProductButton.addPressListener(new PressListener() {
            public void controlPressed(ControlEvent e) {
                deleteProduct();
            }
        });

        
    }
    
    public void loadProducts() {

        try {
            productItems = orderController.getProductsFromOrder(orderId);
            list.removeAll();

            for (int i = 0; i < productItems.length; i++) {
                ProductItem pi = productItems[i];

                list.add(
                    pi.getProductName() +
                    " | " + pi.getPrice() +
                    " | Total: " + pi.getQuantity()
                );

            }

        } catch (Exception e) {
            Toast.show("Erro ao carregar Produtos", 2000);
            System.err.println(e);
        }

    }

    public void deleteProduct() {

        int productSelected = list.getSelectedIndex();

        if(productSelected == -1) {
            Toast.show("Selecione um produto", 2000);
        }

        try {

            int productId = productItems[productSelected].getItemId();
            orderController.deleteProductFromOrder(orderId, productId);
            loadProducts();

        } catch (Exception e) {
            
            Toast.show("Falha ao deletar produto", 2000);
            
        }

    }

}
