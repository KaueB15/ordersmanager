package br.com.sovis.ordersmanager.view.list;

import br.com.sovis.ordersmanager.controller.ProductController;
import br.com.sovis.ordersmanager.controller.UserController;
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
import totalcross.ui.image.Image;

public class ProductUserInfo extends Container {

    private Label mainLabel = new Label("Produtos do Usuário");
    private ListContainer list;
    private Button backButton;
    private Button cancelProductButton;

    private int userId;
    private ProductController productController = new ProductController();
    private UserController userController = new UserController();
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
        add(list, LEFT + 20, AFTER + 15, FILL - 40, FILL - 80);
        loadProducts();

        try {
            cancelProductButton = new Button(new Image("delete.png").getScaledInstance(20, 20));
            cancelProductButton.setBackColor(Color.getRGB(156, 39, 176));
            cancelProductButton.setForeColor(Color.WHITE);
            add(cancelProductButton, RIGHT - 70, BOTTOM - 10, 60, 60);
    
            backButton = new Button(new Image("home.png").getScaledInstance(20, 20));
            backButton.setBackColor(Color.getRGB(244, 67, 54));
            backButton.setForeColor(Color.WHITE);
            add(backButton, RIGHT - 10, BOTTOM - 10, 60, 60);
        } catch(Exception e) {
            System.out.println(e);
        }

        backButton.addPressListener(e ->
            MainWindow.getMainWindow().swap(new ListUserView(user))
        );

        cancelProductButton.addPressListener(e ->
            deleteProduct()
        );
    }

    private void loadProducts() {

        try {
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

        try {
            int productId = productItems[selected].getId();
            userController.deleteProductFromUser(userId, productId);
            MainWindow.getMainWindow().swap(new ProductUserInfo(user, userId));
            Toast.show("Produto removido", 2000);
        } catch (Exception e) {
            Toast.show("Falha ao remover produto", 2000);
        }
    }
}
