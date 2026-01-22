package br.com.sovis.ordersmanager.view;

import br.com.sovis.ordersmanager.controller.CustomerController;
import br.com.sovis.ordersmanager.controller.OrderController;
import br.com.sovis.ordersmanager.controller.ProductController;
import br.com.sovis.ordersmanager.model.Orders;
import br.com.sovis.ordersmanager.model.OrdersProduct;
import br.com.sovis.ordersmanager.model.Product;
import totalcross.sys.Time;
import totalcross.ui.Button;
import totalcross.ui.ComboBox;
import totalcross.ui.Container;
import totalcross.ui.Edit;
import totalcross.ui.Label;
import totalcross.ui.MainWindow;
import totalcross.ui.Toast;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.PressListener;
import totalcross.ui.gfx.Color;

public class OrderView extends Container {

    private Label customerLabel = new Label("Clientes");
    private Label totalLabel = new Label("Total: 0");
    private CustomerController customerController = new CustomerController();
    private ProductController productController = new ProductController();
    private OrderController orderController = new OrderController();
    private ComboBox customersBox;
    private ComboBox productsBox;
    private Edit quantityField = new Edit();
    private Button closeButton = new Button("Fechar Pedido");
    private Button backButton = new Button("Voltar");
    private Button addProductButton = new Button("Add Product");
    

    private Product[] products;
    private OrdersProduct[] items = new OrdersProduct[50];
    private int itemCount = 0;
    private double total = 0;

    public void initUI() {
        try {
            add(customerLabel, LEFT + 20, TOP + 20);
            String[] customersNames = customerController.getCustomersNames();
            customersBox = new ComboBox(customersNames);
            customersBox.setSelectedIndex(0);
            add(customersBox, LEFT + 20, AFTER + 2, FILL - 40, PREFERRED);
        } catch (Exception e) {
            e.printStackTrace();
        }

        add(new Label("Produtos"), LEFT + 20, AFTER + 10);

        try {
            products = productController.findAll();
            String[] productNames = new String[products.length + 1];
            productNames[0] = "Selecione o Produto";
    
            for (int i = 0; i < products.length; i++) {
                productNames[i + 1] = products[i].getName();
            }

            productsBox = new ComboBox(productNames);
            productsBox.setSelectedIndex(0);
            add(productsBox, LEFT + 20, AFTER + 5, FILL - 40, PREFERRED);
            
        } catch (Exception e) {
            e.printStackTrace();
        }


        add(new Label("Quantity"), LEFT + 20, AFTER + 15);
        quantityField.setText("1");
        quantityField.setValidChars("0123456789");
        add(quantityField, LEFT + 20, AFTER + 5, FILL - 40, PREFERRED);

        add(addProductButton, CENTER, AFTER + 15);
        
        add(totalLabel, LEFT + 20, AFTER + 20);
        
        closeButton.setBackColor(Color.MAGENTA);
        closeButton.setForeColor(Color.WHITE);
        add(closeButton, CENTER, AFTER + 10);
        
        backButton.setBackColor(Color.RED);
        backButton.setForeColor(Color.WHITE);
        add(backButton, CENTER, AFTER + 10);
        
        backButton.addPressListener(new PressListener() {   
            public void controlPressed(ControlEvent e) {
                MainWindow.getMainWindow().swap(new HomeView());
            }
        });
        
        closeButton.addPressListener(new PressListener() {
            public void controlPressed(ControlEvent e) {
                closeOrder();
            }
        });
        
        addProductButton.addPressListener(new PressListener() {
            public void controlPressed(ControlEvent e) {
                addProduct();
            }
        });
    }

    private void addProduct() {

        if (productsBox.getSelectedIndex() == 0) {
            Toast.show("Selecione um Produto", 2000);
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(quantityField.getText());
        } catch (Exception e) {
            Toast.show("Quantidade Inválida", 2000);
            return;
        }

        Product product = products[productsBox.getSelectedIndex() - 1];

        OrdersProduct item = new OrdersProduct();
        item.setidProduct(product.getId());
        item.setQuantity(quantity);
        item.setValue(product.getPrice());

        items[itemCount++] = item;

        total += product.getPrice() * quantity;
        totalLabel.setText("Total: " + total);

        productsBox.setSelectedIndex(0);
        quantityField.setText("1");
    }

    private void closeOrder() {

        if (customersBox.getSelectedIndex() == 0) {
            Toast.show("Selecione um Cliente", 2000);
            return;
        }

        if (itemCount == 0) {
            Toast.show("Adicione um produto", 2000);
            return;
        }

        int customerId = customersBox.getSelectedIndex();

        Orders order = new Orders();
        order.setCustomerId(customerId);
        order.setOrderDate(new Time().toString());
        order.setStatus("OPEN");

        OrdersProduct[] finalItems = new OrdersProduct[itemCount];
        for (int i = 0; i < itemCount; i++) {
            finalItems[i] = items[i];
        }

        try {
            orderController.createOrder(order, finalItems);
            Toast.show("Pedido Salvo", 2000);
            MainWindow.getMainWindow().swap(new HomeView());
        } catch (Exception e) {
            Toast.show("Erro ao salvar pedido", 2000);
            e.printStackTrace();
        }
    }


}


