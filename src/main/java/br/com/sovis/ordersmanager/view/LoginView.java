package br.com.sovis.ordersmanager.view;

import br.com.sovis.ordersmanager.controller.UserController;
import totalcross.ui.Button;
import totalcross.ui.Container;
import totalcross.ui.Edit;
import totalcross.ui.Label;
import totalcross.ui.MainWindow;
import totalcross.ui.Toast;
import totalcross.ui.gfx.Color;

public class LoginView extends Container {

    private Label mainLabel = new Label("Login");
    private Edit userEmail = new Edit();
    private Edit userPassword = new Edit();
    private Button loginButton = new Button("Logar");
    private UserController userController = new UserController();

    @Override
    public void initUI() {

        add(mainLabel, CENTER, TOP + 30);

        userEmail.caption = "E-mail";
        add(userEmail, CENTER, AFTER + 20);

        userPassword.caption = "Senha";
        userPassword.setMode(Edit.PASSWORD);
        add(userPassword, CENTER, AFTER + 10);

        loginButton.setBackColor(Color.MAGENTA);
        loginButton.setForeColor(Color.WHITE);
        add(loginButton, CENTER, AFTER + 20);

        loginButton.addPressListener(event -> {
            if(userController.login(userEmail.getText(), userPassword.getText())) {
                Toast.show("Login Realizado", 2000);
                MainWindow.getMainWindow().swap(new HomeView());
            } else {
                Toast.show("Falha no login", 2000);
            }
        });
    }

}