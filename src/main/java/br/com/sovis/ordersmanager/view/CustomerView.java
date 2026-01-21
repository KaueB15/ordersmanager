package br.com.sovis.ordersmanager.view;

import br.com.sovis.ordersmanager.controller.CustomerController;
import totalcross.ui.Button;
import totalcross.ui.Container;
import totalcross.ui.Edit;
import totalcross.ui.Label;
import totalcross.ui.MainWindow;
import totalcross.ui.Toast;
import totalcross.ui.gfx.Color;

public class CustomerView extends Container {

    private Label mainLabel = new Label("Cadastrar Cliente");
    private Edit nameField = new Edit();
    private Edit emailField = new Edit();
    private Edit phoneField = new Edit();
    private Button registerButton = new Button("Cadastrar Cliente");
    private Button backButton = new Button("Voltar");
    private CustomerController customerController = new CustomerController();

    @Override
    public void initUI() {

        add(mainLabel, CENTER, TOP + 10);

        nameField.caption = "Nome";
        add(nameField, CENTER, AFTER + 20);
        
        emailField.caption = "E-mail";
        add(emailField, CENTER, AFTER + 20);

        phoneField.caption = "Telefone";
        add(phoneField, CENTER, AFTER + 20);

        registerButton.setBackColor(Color.MAGENTA);
        registerButton.setForeColor(Color.WHITE);
        add(registerButton, CENTER, AFTER + 30);
        
        backButton.setBackColor(Color.RED);
        backButton.setForeColor(Color.WHITE);
        add(backButton, CENTER, AFTER + 10);

        registerButton.addPressListener(event -> {

            try {
                customerController.createCustomer(nameField.getText(), emailField.getText(), phoneField.getText());
                Toast.show("Cliente cadastrado com sucesso!", 2000);
                nameField.clear();
                emailField.clear();
                phoneField.clear();
            } catch (Exception e) {
                Toast.show("Falha ao cadastrar cliente!", 2000);
                e.printStackTrace();
            }

        });

        backButton.addPressListener(event -> {
            MainWindow.getMainWindow().swap(new HomeView());
        });

    }

}
