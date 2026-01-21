package br.com.sovis.ordersmanager.view;

import br.com.sovis.ordersmanager.controller.ProductController;
import totalcross.ui.Button;
import totalcross.ui.Container;
import totalcross.ui.Edit;
import totalcross.ui.Label;
import totalcross.ui.MainWindow;
import totalcross.ui.Toast;
import totalcross.ui.gfx.Color;

public class ProductView extends Container {

    private Label mainLabel = new Label("Cadastrar Produto");
    private Edit nameField = new Edit();
    private Edit descriptionField = new Edit();
    private Edit priceField = new Edit();
    private Button registerButton = new Button("Cadastrar Produto");
    private Button backButton = new Button("Voltar");
    private ProductController productController = new ProductController();

    @Override
    public void initUI() {

        add(mainLabel, CENTER, TOP + 10);

        nameField.caption = "Nome";
        add(nameField, CENTER, AFTER + 20);
        
        descriptionField.caption = "Descrição";
        add(descriptionField, CENTER, AFTER + 20);

        priceField.caption = "Preço";
        add(priceField, CENTER, AFTER + 20);

        registerButton.setBackColor(Color.MAGENTA);
        registerButton.setForeColor(Color.WHITE);
        add(registerButton, CENTER, AFTER + 30);

        backButton.setBackColor(Color.RED);
        backButton.setForeColor(Color.WHITE);
        add(backButton, CENTER, AFTER + 10);

        registerButton.addPressListener(event -> {

            try {
                productController.createProduct(nameField.getText(), descriptionField.getText(), Double.parseDouble(priceField.getText()));
                Toast.show("Produto cadastrado com sucesso!", 2000);
            } catch (Exception e) {
                Toast.show("Falha ao cadastrar produto!", 2000);
                e.printStackTrace();
            }

        });

        backButton.addPressListener(event -> {
            MainWindow.getMainWindow().swap(new HomeView());
        });

    }

}
