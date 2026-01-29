package br.com.sovis.ordersmanager.view;

import br.com.sovis.ordersmanager.controller.CustomerController;
import totalcross.ui.Button;
import totalcross.ui.Container;
import totalcross.ui.Edit;
import totalcross.ui.Label;
import totalcross.ui.MainWindow;
import totalcross.ui.Toast;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.PressListener;
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
        add(nameField, CENTER, AFTER + 40);
        
        emailField.caption = "E-mail";
        add(emailField, CENTER, AFTER + 40);

        phoneField.caption = "Telefone";
        phoneField.setValidChars("0123456789");
        add(phoneField, CENTER, AFTER + 40);

        registerButton.setBackColor(Color.MAGENTA);
        registerButton.setForeColor(Color.WHITE);
        add(registerButton, CENTER, AFTER + 60);
        
        backButton.setBackColor(Color.RED);
        backButton.setForeColor(Color.WHITE);
        add(backButton, CENTER, AFTER + 10);

        registerButton.addPressListener(new PressListener() {

            public void controlPressed(ControlEvent e) {

                if (!validateCustomerFields(nameField)) {
                    return;
                }

                try {
                    customerController.createCustomer(nameField.getText(), emailField.getText(), phoneField.getText());
                    Toast.show("Cliente cadastrado com sucesso!", 2000);
                    MainWindow.getMainWindow().swap(new ListCustomersView());
                } catch (Exception ex) {
                    Toast.show("Falha ao cadastrar cliente!", 2000);
                }

            }
            
        });

        backButton.addPressListener(new PressListener() {
            
            public void controlPressed(ControlEvent e) {
                
                MainWindow.getMainWindow().swap(new ListCustomersView());

            }

        });

    }

    private boolean validateCustomerFields(Edit nameEdit) {

        if (nameEdit.getText().trim().length() == 0) {
            Toast.show("Nome é obrigatório", 2000);
            nameEdit.requestFocus();
            return false;
        }

        return true;
    }

}