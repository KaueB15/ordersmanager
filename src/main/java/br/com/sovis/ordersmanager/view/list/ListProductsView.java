package br.com.sovis.ordersmanager.view.list;

import br.com.sovis.ordersmanager.controller.ProductController;
import br.com.sovis.ordersmanager.model.Product;
import br.com.sovis.ordersmanager.model.User;
import br.com.sovis.ordersmanager.view.HomeView;
import br.com.sovis.ordersmanager.view.forms.ProductToUserView;
import br.com.sovis.ordersmanager.view.forms.ProductView;
import br.com.sovis.ordersmanager.view.items.ProductItem;
import totalcross.ui.Button;
import totalcross.ui.Container;
import totalcross.ui.Label;
import totalcross.ui.ListContainer;
import totalcross.ui.MainWindow;
import totalcross.ui.Toast;
import totalcross.ui.gfx.Color;
import totalcross.ui.image.Image;

public class ListProductsView extends Container {

    private Label mainLabel = new Label("Produtos");
    private ListContainer list;

    private Button assButton;
    private Button addProductButton = new Button("+");
    private Button editButton;
    private Button backButton;
    private Button removeProductButton;

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
        add(list, LEFT + 20, AFTER + 10, FILL - 40, FILL - 80);
        loadProducts();
        
        try {
            removeProductButton = new Button(new Image("delete.png").getScaledInstance(20, 20));
            assButton = new Button(new Image("products.png").getScaledInstance(20, 20));
            editButton = new Button(new Image("edit.png").getScaledInstance(20, 20));
            
            if (user.getAdmin() == 1) {
                removeProductButton.setBackColor(Color.getRGB(156, 39, 176));
                removeProductButton.setForeColor(Color.WHITE);
                add(removeProductButton, RIGHT - 250, BOTTOM - 10, 60, 60);
                
                assButton.setBackColor(Color.getRGB(46, 204, 113));
                assButton.setForeColor(Color.WHITE);
                assButton.setFont(assButton.getFont().adjustedBy(10));
                add(assButton, RIGHT - 190, BOTTOM - 10, 60, 60);

                editButton.setBackColor(Color.getRGB(46, 204, 113));
                editButton.setForeColor(Color.WHITE);
                editButton.setFont(editButton.getFont().adjustedBy(10));
                add(editButton, RIGHT - 130, BOTTOM - 10, 60, 60);
    
                addProductButton.setBackColor(Color.getRGB(46, 204, 113));
                addProductButton.setForeColor(Color.WHITE);
                addProductButton.setFont(addProductButton.getFont().adjustedBy(10));
                add(addProductButton, RIGHT - 70, BOTTOM - 10, 60, 60);
            }
            
            backButton = new Button(new Image("home.png").getScaledInstance(20, 20));
            backButton.setBackColor(Color.getRGB(244, 67, 54));
            backButton.setForeColor(Color.WHITE);
            add(backButton, RIGHT - 10, BOTTOM - 10, 60, 60);
        } catch(Exception e) {
            System.out.println(e);
        }
        
        backButton.addPressListener(e ->
            MainWindow.getMainWindow().swap(new HomeView(user))
        );
        
        removeProductButton.addPressListener(e ->
            removeProduct()
        );
        
        addProductButton.addPressListener(e ->
            MainWindow.getMainWindow().swap(new ProductView(user))
        );
    
        assButton.addPressListener(e ->
            MainWindow.getMainWindow().swap(new ProductToUserView(user))
        );

        editButton.addPressListener(e -> {
            int selectedIndex = list.getSelectedIndex();

            if (selectedIndex == -1) {
                Toast.show("Selecione um produto", 2000);
                return;
            }

            MainWindow.getMainWindow().swap(new ProductView(user, products[selectedIndex]));
        });

    }
    
    private void loadProducts() {
        try {
            if(user.getAdmin() == 1) {
                products = productController.findAll();
            } else {
                products = productController.findByUserId(user.getId());
            }
            
            list.removeAll();

            for (int i = 0; i < products.length; i++) {
                list.addContainer(new ProductItem(products[i]));
            }

        } catch (Exception e) {
            Toast.show("Erro ao carregar produtos", 2000);
            System.out.println(e);
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
            if(productController.productAlreadyUsed(productId)) {
                Toast.show("Produto sendo utilizado", 2000);
                return;
            }
            productController.removeProduct(productId);
            MainWindow.getMainWindow().swap(new ListProductsView(user));
        } catch (Exception e) {
            Toast.show("Falha ao remover produto", 2000);
            System.out.println(e);
        }
    }
}
