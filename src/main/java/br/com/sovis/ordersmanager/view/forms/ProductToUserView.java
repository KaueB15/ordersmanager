package br.com.sovis.ordersmanager.view.forms;

import br.com.sovis.ordersmanager.controller.ProductController;
import br.com.sovis.ordersmanager.controller.UserController;
import br.com.sovis.ordersmanager.model.Product;
import br.com.sovis.ordersmanager.model.User;
import br.com.sovis.ordersmanager.model.UsersProduct;
import br.com.sovis.ordersmanager.view.list.ListOrderView;
import br.com.sovis.ordersmanager.view.list.ListProductsView;
import totalcross.ui.Button;
import totalcross.ui.ComboBox;
import totalcross.ui.Container;
import totalcross.ui.Label;
import totalcross.ui.MainWindow;
import totalcross.ui.Toast;
import totalcross.ui.font.Font;
import totalcross.ui.gfx.Color;

public class ProductToUserView extends Container {
    private Label mainLabel = new Label("Usuários e Produtos");

    private ComboBox userBox;
    private ComboBox productsBox;

    private Button addProductButton = new Button("Adicionar Produto");
    private Button saveButton = new Button("Salvar");
    private Button cancelButton = new Button("Cancelar");

    private Container footer = new Container();

    private UserController userController = new UserController();
    private ProductController productController = new ProductController();

    private User[] users;
    private Product[] products;
    private UsersProduct[] items = new UsersProduct[100];
    private int itemCount = 0;

    private int orderId;
    private User user;

    int boxWidth = 300;
    int boxHeight = 50;
    int padding = 8;

    public ProductToUserView(User user) {
        this.orderId = 0;
        this.user = user;
    }

    @Override
    public void initUI() {

        setBackColor(Color.getRGB(242, 247, 244));

        mainLabel.setForeColor(Color.getRGB(46, 204, 113));
        mainLabel.setFont(Font.getFont(true, 22));
        add(mainLabel, CENTER, TOP + 25);

        try {
            Label userLabel = new Label("Usuários");
            userLabel.setForeColor(Color.getRGB(44, 62, 80));
            add(userLabel, LEFT + 40, AFTER + 35);

            users = userController.findAll();
            String[] names = new String[users.length];

            for (int i = 0; i < users.length; i++) {
                names[i] = users[i].getEmail();
            }

            userBox = new ComboBox(names);
            addCombo(userBox);

            if (orderId != 0) {
                userBox.setEnabled(false);
            }

        } catch (Exception e) {
            Toast.show("Erro ao carregar clientes", 2000);
        }

        try {
            Label productLabel = new Label("Produto");
            productLabel.setForeColor(Color.getRGB(44, 62, 80));
            add(productLabel, LEFT + 40, AFTER + 20);

            products = productController.findAll();
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

        addProductButton.setBackColor(Color.getRGB(156, 39, 176));
        addProductButton.setForeColor(Color.WHITE);
        add(addProductButton, CENTER, AFTER + 25, 220, 45);

        footer.setBackColor(Color.WHITE);
        add(footer, LEFT, BOTTOM, FILL, 70);

        saveButton.setBackColor(Color.getRGB(46, 204, 113));
        saveButton.setForeColor(Color.WHITE);
        footer.add(saveButton, LEFT + 10, CENTER, (footer.getWidth() / 2) - 15, 45);

        cancelButton.setBackColor(Color.getRGB(244, 67, 54));
        cancelButton.setForeColor(Color.WHITE);
        footer.add(cancelButton, AFTER + 10, SAME, (footer.getWidth() / 2) - 15, 45);

        addProductButton.addPressListener(e -> addProduct());
        saveButton.addPressListener(e -> saveAssociation());
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

    private void addProduct() {

        if (productsBox.getSelectedIndex() == 0) {
            Toast.show("Selecione um produto", 2000);
            return;
        }

        Product product = products[productsBox.getSelectedIndex() - 1];
        int userId = users[userBox.getSelectedIndex()].getId();

        UsersProduct item = new UsersProduct();
        item.setIdProduct(product.getId());
        item.setIdUser(userId);
        try {
            if(userController.productAlreadyAssocieated(item)) {
                Toast.show("Produto já associado", 2000);
                return;
            }
            items[itemCount++] = item;
    
            productsBox.setSelectedIndex(0);
        } catch (Exception e) {
            Toast.show("Erro ao adicionar produto", 2000);
            System.err.println(e);
        }

    }

    private void saveAssociation() {
        if(itemCount == 0) {
            Toast.show("Adicione um produto", 2000);
            return;
        }

        try {
            UsersProduct[] finalItems = new UsersProduct[itemCount];
            for (int i = 0; i < itemCount; i++) {
                finalItems[i] = items[i];
            }
            userController.createUserProductAssociation(finalItems);
            Toast.show("Produtos associados com sucesso", 2000);
            MainWindow.getMainWindow().swap(new ListProductsView(user));
        } catch (Exception e) {
            System.err.println(e);
            Toast.show("Produtos não associados", 2000);
        }
    }

}
