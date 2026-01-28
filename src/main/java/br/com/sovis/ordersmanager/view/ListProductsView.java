package br.com.sovis.ordersmanager.view;

import br.com.sovis.ordersmanager.controller.ProductController;
import br.com.sovis.ordersmanager.model.Product;
import br.com.sovis.ordersmanager.view.items.ProductItem;
import totalcross.ui.Button;
import totalcross.ui.Container;
import totalcross.ui.Label;
import totalcross.ui.ListContainer;
import totalcross.ui.MainWindow;
import totalcross.ui.Toast;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.PressListener;
import totalcross.ui.gfx.Color;

public class ListProductsView extends Container {

    private Label mainLabel = new Label("Produtos");
    private ListContainer list;
    private Container buttonRows = new Container();
    private Button backButton = new Button("Voltar");
    private Button removeCustomerButton = new Button("Remover");
    private ProductController productController = new ProductController();

    private Product[] products;

    @Override
    public void initUI() {

        add(mainLabel, CENTER, TOP + 20);

        list = new ListContainer();
        
        add(list, LEFT + 20, AFTER + 10, FILL - 40, FILL - 150);
        loadProducts();

        add(buttonRows, LEFT + 20, AFTER + 10, FILL - 40, 45);

        removeCustomerButton.setBackColor(Color.MAGENTA);
        removeCustomerButton.setForeColor(Color.WHITE);
        buttonRows.add(removeCustomerButton, LEFT, TOP, (buttonRows.getWidth() / 2) -5, PREFERRED);
        backButton.setBackColor(Color.RED);
        backButton.setForeColor(Color.WHITE);
        buttonRows.add(backButton, RIGHT, TOP, (buttonRows.getWidth() / 2) -5, PREFERRED);

        backButton.addPressListener(new PressListener() {
            public void controlPressed(ControlEvent e) {
                MainWindow.getMainWindow().swap(new HomeView());
            }
        });

        removeCustomerButton.addPressListener(new PressListener() {
            public void controlPressed(ControlEvent e) {
                removeCustomer();
            }
        });

    }

    private void loadProducts() {

        try {
            products = productController.findAll();
            list.removeAll();

            for (int i = 0; i < products.length; i++) {
                ProductItem item = new ProductItem(products[i]);

                if (i == 0) {
                    list.addContainer(item);
                } else {
                    list.addContainer(item);
                }

            }

        } catch (Exception e) {
            Toast.show("Erro ao carregar clientes", 2000);
            System.err.println(e);
        }

    }

    private void removeCustomer() {

        int productselected = list.getSelectedIndex();
        ProductItem productItem = (ProductItem) list.getSelectedItem();

        if(productselected == -1) {
            Toast.show("Selecione um cliente", 2000);
        }

        try {

            int productId = products[productselected].getId();
            productController.removeProduct(productId);
            productItem.removeAll();

        } catch (Exception e) {
            
            Toast.show("Falha ao remover cliente", 2000);
            
        }

    }

}
