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
        add(nameField, CENTER, AFTER + 40);
        
        descriptionField.caption = "Descrição";
        add(descriptionField, CENTER, AFTER + 40);

        priceField.caption = "Preço";
        priceField.setText("1");
        priceField.setValidChars("0123456789");
        add(priceField, CENTER, AFTER + 40);

        registerButton.setBackColor(Color.MAGENTA);
        registerButton.setForeColor(Color.WHITE);
        add(registerButton, CENTER, AFTER + 60);

        backButton.setBackColor(Color.RED);
        backButton.setForeColor(Color.WHITE);
        add(backButton, CENTER, AFTER + 10);

        registerButton.addPressListener(event -> {

            if (!validateProductFields(nameField, priceField)) {
                return;
            }

            try {
                productController.createProduct(nameField.getText(), descriptionField.getText(), Double.parseDouble(priceField.getText()));
                Toast.show("Produto cadastrado com sucesso!", 2000);
            } catch (Exception e) {
                Toast.show("Falha ao cadastrar produto!", 2000);
                e.printStackTrace();
            }

        });

        backButton.addPressListener(event -> {
            MainWindow.getMainWindow().swap(new ListProductsView());
        });

    }

    private boolean validateProductFields(Edit nameEdit, Edit priceEdit) {

        if (nameEdit.getText().trim().length() == 0) {
            Toast.show("Nome do produto é obrigatório", 2000);
            nameEdit.requestFocus();
            return false;
        }

        double price;

        try {
            price = Double.parseDouble(priceEdit.getText());
        } catch (Exception e) {
            Toast.show("Preo inválido", 2000);
            priceEdit.requestFocus();
            return false;
        }

        if (price <= 0) {
            Toast.show("Preço tem que ser maior que 0", 2000);
            priceEdit.requestFocus();
            return false;
        }

        return true;
    }

}
