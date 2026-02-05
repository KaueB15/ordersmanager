package br.com.sovis.ordersmanager.view;

import br.com.sovis.ordersmanager.controller.ProductController;
import br.com.sovis.ordersmanager.model.Product;
import br.com.sovis.ordersmanager.model.User;
import br.com.sovis.ordersmanager.view.items.UserProductItem;
import totalcross.ui.Button;
import totalcross.ui.Container;
import totalcross.ui.Label;
import totalcross.ui.ListContainer;
import totalcross.ui.MainWindow;
import totalcross.ui.Toast;
import totalcross.ui.gfx.Color;

public class ProductUserInfo extends Container {

    private Label mainLabel = new Label("Produtos do Usuário");
    private ListContainer list;
    private Container bottomBar = new Container();
    private Button backButton = new Button("Voltar");
    private Button cancelProductButton = new Button("Retirar Produto");

    private int userId;
    private ProductController productController = new ProductController();
    private Product[] productItems;
    private User user;

    public ProductUserInfo(User user, int userId) {
        this.userId = userId;
        this.user = user;
    }

    @Override
    public void initUI() {

        setBackColor(Color.getRGB(240, 248, 243));

        mainLabel.setForeColor(Color.getRGB(39, 174, 96));
        add(mainLabel, CENTER, TOP + 20);

        list = new ListContainer();
        add(list, LEFT + 20, AFTER + 15, FILL - 40, FILL - 130);
        loadProducts();

        add(bottomBar, LEFT, BOTTOM, FILL, 70);
        bottomBar.setBackColor(Color.getRGB(255, 255, 255));

        cancelProductButton.setBackColor(Color.getRGB(39, 174, 96));
        cancelProductButton.setForeColor(Color.WHITE);
        bottomBar.add(cancelProductButton, LEFT + 10, CENTER, bottomBar.getWidth() / 2 - 15, 45);

        backButton.setBackColor(Color.getRGB(231, 76, 60));
        backButton.setForeColor(Color.WHITE);
        bottomBar.add(backButton, RIGHT - 10, CENTER, bottomBar.getWidth() / 2 - 15, 45);

        backButton.addPressListener(e ->
            MainWindow.getMainWindow().swap(new ListUserView(user))
        );

        cancelProductButton.addPressListener(e ->
            deleteProduct()
        );
    }

    private void loadProducts() {

        try {
            System.err.println(userId);
            productItems = productController.findByUserId(userId);
            list.removeAll();

            for (int i = 0; i < productItems.length; i++) {
                list.addContainer(new UserProductItem(productItems[i]));
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

        // try {
        //     int productId = productItems[selected].getItemId();
        //     orderController.deleteProductFromOrder(orderId, productId);
        //     MainWindow.getMainWindow().swap(new OrderInfoView(orderId, status, user));
        // } catch (Exception e) {
        //     Toast.show("Falha ao remover produto", 2000);
        // }
    }
}
