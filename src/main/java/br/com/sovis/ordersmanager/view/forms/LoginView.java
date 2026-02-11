package br.com.sovis.ordersmanager.view.forms;

import br.com.sovis.ordersmanager.controller.UserController;
import br.com.sovis.ordersmanager.model.User;
import br.com.sovis.ordersmanager.view.HomeView;
import totalcross.ui.Button;
import totalcross.ui.Container;
import totalcross.ui.Edit;
import totalcross.ui.Label;
import totalcross.ui.MainWindow;
import totalcross.ui.Toast;
import totalcross.ui.font.Font;
import totalcross.ui.gfx.Color;

public class LoginView extends Container {

    private Label mainLabel = new Label("Login");
    private Label emailLabel = new Label("Nome de Usuário");
    private Label passwordLabel = new Label("Senha");

    private Container emailBox = new Container();
    private Container passwordBox = new Container();

    private Edit userEmail = new Edit();
    private Edit userPassword = new Edit();

    private Button loginButton = new Button("Logar");
    private UserController userController = new UserController();

    int boxWidth = 280;
    int boxHeight = 50;
    int padding = 8;

    @Override
    public void initUI() {

        setBackColor(Color.getRGB(240, 248, 243));

        mainLabel.setForeColor(Color.getRGB(39, 174, 96));
        mainLabel.setFont(Font.getFont(true, 22));
        add(mainLabel, CENTER, TOP + 30);

        emailLabel.setForeColor(Color.getRGB(44, 62, 80));
        add(emailLabel, LEFT + 40, AFTER + 60);

        emailBox.setBackColor(Color.WHITE);
        emailBox.setBorderStyle(Container.BORDER_ROUNDED);
        emailBox.borderColor = Color.getRGB(39, 174, 96);
        add(emailBox, CENTER, AFTER + 6, boxWidth, boxHeight);

        Container emailInner = new Container();
        emailInner.setBackColor(Color.WHITE);
        emailBox.add(emailInner, LEFT + padding, TOP + padding,
                boxWidth - padding * 2, boxHeight - padding * 2);

        userEmail.setBackColor(Color.WHITE);
        userEmail.setForeColor(Color.getRGB(44, 62, 80));
        userEmail.transparentBackground = false;
        emailInner.add(userEmail, LEFT, TOP,
                FILL, boxHeight - padding * 2);

        passwordLabel.setForeColor(Color.getRGB(44, 62, 80));
        add(passwordLabel, LEFT + 40, AFTER + 16);

        passwordBox.setBackColor(Color.WHITE);
        passwordBox.setBorderStyle(Container.BORDER_ROUNDED);
        passwordBox.borderColor = Color.getRGB(39, 174, 96);
        add(passwordBox, CENTER, AFTER + 6, boxWidth, boxHeight);

        Container passwordInner = new Container();
        passwordInner.setBackColor(Color.WHITE);
        passwordBox.add(passwordInner, LEFT + padding, TOP + padding,
                boxWidth - padding * 2, boxHeight - padding * 2);

        userPassword.setMode(Edit.PASSWORD);
        userPassword.setBackColor(Color.WHITE);
        userPassword.setForeColor(Color.getRGB(44, 62, 80));
        userPassword.transparentBackground = false;
        passwordInner.add(userPassword, LEFT, TOP,
                FILL, boxHeight - padding * 2);

        loginButton.setBackColor(Color.getRGB(39, 174, 96));
        loginButton.setForeColor(Color.WHITE);
        loginButton.setFont(Font.getFont(true, 16));
        add(loginButton, CENTER, AFTER + 40, 200, 48);

        loginButton.addPressListener(event -> {
            User user = userController.login(userEmail.getText(), userPassword.getText());
            if (!(user == null)) {
                Toast.show("Login Realizado", 2000);
                MainWindow.getMainWindow().swap(new HomeView(user));
            } else {
                Toast.show("Falha no login", 2000);
            }
        });
    }
}
