package br.com.sovis.ordersmanager.view;

import br.com.sovis.ordersmanager.controller.ProductController;
import br.com.sovis.ordersmanager.model.Product;
import br.com.sovis.ordersmanager.model.User;
import br.com.sovis.ordersmanager.view.items.ProductItem;
import totalcross.ui.Button;
import totalcross.ui.Container;
import totalcross.ui.Label;
import totalcross.ui.ListContainer;
import totalcross.ui.MainWindow;
import totalcross.ui.Toast;
import totalcross.ui.gfx.Color;

public class ListProductsView extends Container {

    private Label mainLabel = new Label("Produtos");
    private ListContainer list;
    private Container buttonRows = new Container();

    private Button backButton = new Button("Voltar");
    private Button removeProductButton = new Button("Remover");

    private ProductController productController = new ProductController();
    private Product[] products;
    private User user;

    public ListProductsView(User user) {
        this.user = user;
    }

    @Override
    public void initUI() {

        setBackColor(Color.getRGB(242, 247, 244));

        mainLabel.setForeColor(Color.getRGB(46, 204, 113));
        mainLabel.setFont(mainLabel.getFont().adjustedBy(6));
        add(mainLabel, CENTER, TOP + 20);

        list = new ListContainer();
        add(list, LEFT + 20, AFTER + 10, FILL - 40, FILL - 130);
        loadProducts();

        buttonRows.setBackColor(Color.WHITE);
        add(buttonRows, LEFT, BOTTOM, FILL, 60);

        removeProductButton.setBackColor(Color.getRGB(156, 39, 176));
        removeProductButton.setForeColor(Color.WHITE);
        buttonRows.add(
            removeProductButton,
            LEFT + 10,
            CENTER,
            (buttonRows.getWidth() / 2) - 15,
            45
        );

        backButton.setBackColor(Color.getRGB(244, 67, 54));
        backButton.setForeColor(Color.WHITE);
        buttonRows.add(
            backButton,
            AFTER + 10,
            SAME,
            (buttonRows.getWidth() / 2) - 15,
            45
        );

        Button addButton = new Button("+");
        addButton.setBackColor(Color.getRGB(46, 204, 113));
        addButton.setForeColor(Color.WHITE);
        addButton.setFont(addButton.getFont().adjustedBy(10));
        add(addButton, RIGHT - 20, BOTTOM - 60, 60, 60);

        Button assButton = new Button("=");
        assButton.setBackColor(Color.getRGB(46, 204, 113));
        assButton.setForeColor(Color.WHITE);
        assButton.setFont(assButton.getFont().adjustedBy(10));
        add(assButton, RIGHT - 80, BOTTOM - 60, 60, 60);

        backButton.addPressListener(e ->
            MainWindow.getMainWindow().swap(new HomeView(user))
        );

        removeProductButton.addPressListener(e ->
            removeProduct()
        );

        addButton.addPressListener(e ->
            MainWindow.getMainWindow().swap(new ProductView(user))
        );

        assButton.addPressListener(e ->
            MainWindow.getMainWindow().swap(new ProductToUserView(user))
        );
        
    }

    private void loadProducts() {
        try {
            products = productController.findAll();
            list.removeAll();

            for (int i = 0; i < products.length; i++) {
                list.addContainer(new ProductItem(products[i]));
            }

        } catch (Exception e) {
            Toast.show("Erro ao carregar produtos", 2000);
        }
    }

    private void removeProduct() {

        int selectedIndex = list.getSelectedIndex();

        if (selectedIndex == -1) {
            Toast.show("Selecione um produto", 2000);
            return;
        }

        try {
            int productId = products[selectedIndex].getId();
            productController.removeProduct(productId);
            MainWindow.getMainWindow().swap(new ListProductsView(user));
        } catch (Exception e) {
            Toast.show("Falha ao remover produto", 2000);
        }
    }
}
