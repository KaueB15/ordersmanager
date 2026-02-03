package br.com.sovis.ordersmanager.view;

import br.com.sovis.ordersmanager.controller.ProductController;
import br.com.sovis.ordersmanager.model.User;
import totalcross.ui.Button;
import totalcross.ui.Container;
import totalcross.ui.Edit;
import totalcross.ui.Label;
import totalcross.ui.MainWindow;
import totalcross.ui.Toast;
import totalcross.ui.font.Font;
import totalcross.ui.gfx.Color;

public class ProductView extends Container {

    private Label mainLabel = new Label("Cadastrar Produto");

    private Edit nameField = new Edit();
    private Edit descriptionField = new Edit();
    private Edit priceField = new Edit();

    private Button registerButton = new Button("Salvar");
    private Button backButton = new Button("Voltar");

    private Container footer = new Container();
    private ProductController productController = new ProductController();
    private User user;

    int boxWidth = 300;
    int boxHeight = 50;
    int padding = 8;

    public ProductView(User user) {
        this.user = user;
    }

    @Override
    public void initUI() {

        setBackColor(Color.getRGB(242, 247, 244));

        mainLabel.setForeColor(Color.getRGB(46, 204, 113));
        mainLabel.setFont(Font.getFont(true, 22));
        add(mainLabel, CENTER, TOP + 25);

        addField("Nome", nameField, AFTER + 40);
        addField("Descrição", descriptionField, AFTER + 20);
        addField("Preço", priceField, AFTER + 20);

        priceField.setValidChars("0123456789");

        footer.setBackColor(Color.WHITE);
        add(footer, LEFT, BOTTOM - 70, FILL, 70);

        registerButton.setBackColor(Color.getRGB(46, 204, 113));
        registerButton.setForeColor(Color.WHITE);
        footer.add(registerButton, LEFT + 10, CENTER, (footer.getWidth() / 2) - 15, 45);

        backButton.setBackColor(Color.getRGB(244, 67, 54));
        backButton.setForeColor(Color.WHITE);
        footer.add(backButton, AFTER + 10, SAME, (footer.getWidth() / 2) - 15, 45);

        registerButton.addPressListener(e -> saveProduct());
        backButton.addPressListener(e ->
            MainWindow.getMainWindow().swap(new ListProductsView(user))
        );
    }

    private void addField(String labelText, Edit field, int posY) {

        Label label = new Label(labelText);
        label.setForeColor(Color.getRGB(44, 62, 80));
        add(label, LEFT + 40, posY);

        Container box = new Container();
        box.setBackColor(Color.WHITE);
        box.setBorderStyle(Container.BORDER_ROUNDED);
        box.borderColor = Color.getRGB(46, 204, 113);
        add(box, CENTER, AFTER + 6, boxWidth, boxHeight);

        Container inner = new Container();
        inner.setBackColor(Color.WHITE);
        box.add(inner, LEFT + padding, TOP + padding,
                boxWidth - padding * 2, boxHeight - padding * 2);

        field.setBackColor(Color.WHITE);
        field.setForeColor(Color.getRGB(44, 62, 80));
        field.transparentBackground = false;
        inner.add(field, LEFT, TOP, FILL, FILL);
    }

    private void saveProduct() {

        if (!validateFields()) {
            return;
        }

        try {
            productController.createProduct(
                nameField.getText(),
                descriptionField.getText(),
                Double.parseDouble(priceField.getText())
            );

            Toast.show("Produto cadastrado com sucesso", 2000);
            MainWindow.getMainWindow().swap(new ListProductsView(user));

        } catch (Exception e) {
            Toast.show("Erro ao cadastrar produto", 2000);
        }
    }

    private boolean validateFields() {

        if (nameField.getText().trim().length() == 0) {
            Toast.show("Nome do produto é obrigatório", 2000);
            nameField.requestFocus();
            return false;
        }

        double price;

        try {
            price = Double.parseDouble(priceField.getText());
        } catch (Exception e) {
            Toast.show("Preço inválido", 2000);
            priceField.requestFocus();
            return false;
        }

        if (price <= 0) {
            Toast.show("Preço deve ser maior que zero", 2000);
            priceField.requestFocus();
            return false;
        }

        return true;
    }
}
