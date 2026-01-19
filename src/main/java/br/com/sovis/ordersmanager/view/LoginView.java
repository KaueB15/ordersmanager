package br.com.sovis.ordersmanager.view;

import totalcross.ui.Button;
import totalcross.ui.Container;
import totalcross.ui.Edit;
import totalcross.ui.Label;
import totalcross.ui.gfx.Color;

public class LoginView extends Container {

    private Label mainLabel = new Label("Login");
    private Edit userEmail = new Edit();
    private Edit userPassword = new Edit();
    private Button loginButton = new Button("Logar");

    @Override
    public void initUI() {

        add(mainLabel, CENTER, TOP + 100);

        userEmail.caption = "E-mail";
        add(userEmail, CENTER, AFTER + 100);

        userPassword.caption = "Senha";
        userPassword.setMode(Edit.PASSWORD);
        add(userPassword, CENTER, AFTER + 40);

        loginButton.setBackColor(Color.MAGENTA);
        loginButton.setForeColor(Color.WHITE);
        add(loginButton, CENTER, AFTER + 80);
    }

}