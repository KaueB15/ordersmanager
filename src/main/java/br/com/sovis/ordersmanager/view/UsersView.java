package br.com.sovis.ordersmanager.view;

import br.com.sovis.ordersmanager.controller.UserController;
import totalcross.ui.Button;
import totalcross.ui.Container;
import totalcross.ui.Edit;
import totalcross.ui.Label;
import totalcross.ui.Toast;
import totalcross.ui.font.Font;
import totalcross.ui.gfx.Color;

public class UsersView extends Container {

    private Label mainLabel = new Label("Criar Usuário");
    private Label emailLabel = new Label("E-mail");
    private Label passwordLabel = new Label("Senha");

    private Container emailBox = new Container();
    private Container passwordBox = new Container();
    private Container footer = new Container();

    private Edit userEmail = new Edit();
    private Edit userPassword = new Edit();

    private Button registerButton = new Button("Criar");
    private Button backButton = new Button("Voltar");
    private UserController userController = new UserController();

    int boxWidth = 280;
    int boxHeight = 50;
    int padding = 8;

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

        footer.setBackColor(Color.WHITE);
        add(footer, LEFT, BOTTOM - 70, FILL, 70);

        registerButton.setBackColor(Color.getRGB(46, 204, 113));
        registerButton.setForeColor(Color.WHITE);
        footer.add(registerButton, LEFT + 10, CENTER, (footer.getWidth() / 2) - 15, 45);

        backButton.setBackColor(Color.getRGB(244, 67, 54));
        backButton.setForeColor(Color.WHITE);
        footer.add(backButton, AFTER + 10, SAME, (footer.getWidth() / 2) - 15, 45);

        registerButton.addPressListener(event -> {
            createUser();
        });

    }

    private void createUser() {

        try {
            userController.createUser(userEmail.getText(), userPassword.getText());
            Toast.show("Usuário criado com sucesso", 2000);  
        } catch (Exception e) {
            Toast.show("Erro ao criar usuário", 2000);
        }

    }

}
