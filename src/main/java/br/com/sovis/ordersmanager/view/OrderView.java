package br.com.sovis.ordersmanager.view;

import br.com.sovis.ordersmanager.controller.CustomerController;
import br.com.sovis.ordersmanager.controller.OrderController;
import br.com.sovis.ordersmanager.controller.ProductController;
import br.com.sovis.ordersmanager.model.Customer;
import br.com.sovis.ordersmanager.model.Orders;
import br.com.sovis.ordersmanager.model.OrdersProduct;
import br.com.sovis.ordersmanager.model.Product;
import br.com.sovis.ordersmanager.model.User;
import totalcross.sys.Time;
import totalcross.ui.Button;
import totalcross.ui.ComboBox;
import totalcross.ui.Container;
import totalcross.ui.Edit;
import totalcross.ui.Label;
import totalcross.ui.MainWindow;
import totalcross.ui.Toast;
import totalcross.ui.font.Font;
import totalcross.ui.gfx.Color;

public class OrderView extends Container {

    private Label mainLabel = new Label("Pedido");
    private Label totalLabel = new Label("Total: R$ 0,00");

    private ComboBox customersBox;
    private ComboBox productsBox;

    private Edit quantityField = new Edit();

    private Button addProductButton = new Button("Adicionar Produto");
    private Button saveButton = new Button("Salvar");
    private Button cancelButton = new Button("Cancelar");

    private Container footer = new Container();

    private CustomerController customerController = new CustomerController();
    private ProductController productController = new ProductController();
    private OrderController orderController = new OrderController();

    private Customer[] customers;
    private Product[] products;
    private OrdersProduct[] items = new OrdersProduct[50];
    private int itemCount = 0;
    private double total = 0;

    private int orderId;
    private User user;

    int boxWidth = 300;
    int boxHeight = 50;
    int padding = 8;

    public OrderView(User user) {
        this.orderId = 0;
        this.user = user;
    }

    public OrderView(int orderId, User user) {
        this.orderId = orderId;
        this.user = user;
    }

    @Override
    public void initUI() {

        setBackColor(Color.getRGB(242, 247, 244));

        mainLabel.setForeColor(Color.getRGB(46, 204, 113));
        mainLabel.setFont(Font.getFont(true, 22));
        add(mainLabel, CENTER, TOP + 25);

        try {
            Label customerLabel = new Label("Cliente");
            customerLabel.setForeColor(Color.getRGB(44, 62, 80));
            add(customerLabel, LEFT + 40, AFTER + 35);

            customers = customerController.findAll();
            String[] names = new String[customers.length];

            for (int i = 0; i < customers.length; i++) {
                names[i] = customers[i].getName();
            }

            customersBox = new ComboBox(names);
            addCombo(customersBox);

            if (orderId != 0) {
                customersBox.setEnabled(false);
            }

        } catch (Exception e) {
            Toast.show("Erro ao carregar clientes", 2000);
        }

        try {
            Label productLabel = new Label("Produto");
            productLabel.setForeColor(Color.getRGB(44, 62, 80));
            add(productLabel, LEFT + 40, AFTER + 20);

            if(user.getAdmin() == 1) {
                products = productController.findAll();
            } else {
                products = productController.findByUserId(user.getId());
            }
            String[] names = new String[products.length + 1];
            names[0] = "Selecione o Produto";

            for (int i = 0; i < products.length; i++) {
                names[i + 1] = products[i].getName();
            }

            productsBox = new ComboBox(names);
            addCombo(productsBox);
        } catch (Exception e) {
            Toast.show("Erro ao carregar produtos", 2000);
        }

        Label qtyLabel = new Label("Quantidade");
        qtyLabel.setForeColor(Color.getRGB(44, 62, 80));
        add(qtyLabel, LEFT + 40, AFTER + 20);

        quantityField.setText("1");
        quantityField.setValidChars("0123456789");
        addEdit(quantityField);

        addProductButton.setBackColor(Color.getRGB(156, 39, 176));
        addProductButton.setForeColor(Color.WHITE);
        add(addProductButton, CENTER, AFTER + 25, 220, 45);

        totalLabel.setForeColor(Color.getRGB(44, 62, 80));
        totalLabel.setFont(totalLabel.getFont().adjustedBy(4));
        add(totalLabel, LEFT + 20, AFTER + 25, FILL, PREFERRED);

        footer.setBackColor(Color.WHITE);
        add(footer, LEFT, BOTTOM, FILL, 70);

        saveButton.setBackColor(Color.getRGB(46, 204, 113));
        saveButton.setForeColor(Color.WHITE);
        footer.add(saveButton, LEFT + 10, CENTER, (footer.getWidth() / 2) - 15, 45);

        cancelButton.setBackColor(Color.getRGB(244, 67, 54));
        cancelButton.setForeColor(Color.WHITE);
        footer.add(cancelButton, AFTER + 10, SAME, (footer.getWidth() / 2) - 15, 45);

        addProductButton.addPressListener(e -> addProduct());
        saveButton.addPressListener(e -> saveOrder());
        cancelButton.addPressListener(e ->
            MainWindow.getMainWindow().swap(new ListOrderView(user))
        );
    }

    private void addCombo(ComboBox box) {

        Container container = new Container();
        container.setBackColor(Color.WHITE);
        container.setBorderStyle(Container.BORDER_ROUNDED);
        container.borderColor = Color.getRGB(46, 204, 113);
        add(container, CENTER, AFTER + 6, boxWidth, boxHeight);

        container.add(box, LEFT + padding, TOP + padding,
                boxWidth - padding * 2, boxHeight - padding * 2);
    }

    private void addEdit(Edit field) {

        Container container = new Container();
        container.setBackColor(Color.WHITE);
        container.setBorderStyle(Container.BORDER_ROUNDED);
        container.borderColor = Color.getRGB(46, 204, 113);
        add(container, CENTER, AFTER + 6, boxWidth, boxHeight);

        field.setBackColor(Color.WHITE);
        field.transparentBackground = false;

        container.add(field, LEFT + padding, TOP + padding,
                boxWidth - padding * 2, boxHeight - padding * 2);
    }

    private void addProduct() {

        if (productsBox.getSelectedIndex() == 0) {
            Toast.show("Selecione um produto", 2000);
            return;
        }

        int qty;
        try {
            qty = Integer.parseInt(quantityField.getText());
        } catch (Exception e) {
            Toast.show("Quantidade inválida", 2000);
            return;
        }

        Product product = products[productsBox.getSelectedIndex() - 1];

        OrdersProduct item = new OrdersProduct();
        item.setidProduct(product.getId());
        item.setQuantity(qty);
        item.setValue(product.getPrice());

        items[itemCount++] = item;
        total += product.getPrice() * qty;
        totalLabel.setText("Total: R$ " + total);

        productsBox.setSelectedIndex(0);
        quantityField.setText("1");
    }

    private void saveOrder() {

        if (itemCount == 0) {
            Toast.show("Adicione um produto", 2000);
            return;
        }

        try {
            if (orderId == 0) {

                Orders order = new Orders();
                order.setCustomerId(
                    customers[customersBox.getSelectedIndex()].getId()
                );
                order.setUserId(user.getId());
                order.setOrderDate(new Time().toString());
                order.setStatus("ABERTO");

                OrdersProduct[] finalItems = new OrdersProduct[itemCount];
                for (int i = 0; i < itemCount; i++) {
                    finalItems[i] = items[i];
                }

                orderController.createOrder(order, finalItems);

            } else {

                for (int i = 0; i < itemCount; i++) {
                    orderController.addProductToOrder(
                        orderId,
                        items[i].getidProduct(),
                        items[i].getValue(),
                        items[i].getQuantity()
                    );
                }
            }

            Toast.show("Pedido salvo com sucesso", 2000);
            MainWindow.getMainWindow().swap(new ListOrderView(user));

        } catch (Exception e) {
            Toast.show("Erro ao salvar pedido", 2000);
            System.err.println(e);
        }
    }
}
