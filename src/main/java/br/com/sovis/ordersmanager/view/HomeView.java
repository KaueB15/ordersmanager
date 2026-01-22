package br.com.sovis.ordersmanager.view;

import totalcross.ui.Button;
import totalcross.ui.Container;
import totalcross.ui.Label;
import totalcross.ui.MainWindow;
import totalcross.ui.gfx.Color;

public class HomeView extends Container {

    private Label mainLabel = new Label("Bem vindo ao sistema!");
    private Button customerButton = new Button("Clientes");
    private Button productsButton = new Button("Produtos");
    private Button ordersButton = new Button("Pedidos");

    @Override
    public void initUI() {

        add(mainLabel, CENTER, TOP + 20);

        customerButton.setBackColor(Color.MAGENTA);
        customerButton.setForeColor(Color.WHITE);
        add(customerButton, CENTER, AFTER + 20);

        productsButton.setBackColor(Color.MAGENTA);
        productsButton.setForeColor(Color.WHITE);
        add(productsButton, CENTER, AFTER + 20);

        ordersButton.setBackColor(Color.MAGENTA);
        ordersButton.setForeColor(Color.WHITE);
        add(ordersButton, CENTER, AFTER + 20);
        
        customerButton.addPressListener(event -> {
            MainWindow.getMainWindow().swap(new CustomerView());
        });

        productsButton.addPressListener(event -> {
            MainWindow.getMainWindow().swap(new ProductView());
        });

        ordersButton.addPressListener(event -> {
            MainWindow.getMainWindow().swap(new OrderView());
        });

    }

}
