package br.com.sovis.ordersmanager.view;

import br.com.sovis.ordersmanager.controller.UserController;
import br.com.sovis.ordersmanager.model.User;
import br.com.sovis.ordersmanager.view.items.UserItem;
import totalcross.ui.Button;
import totalcross.ui.Container;
import totalcross.ui.Label;
import totalcross.ui.ListContainer;
import totalcross.ui.MainWindow;
import totalcross.ui.Toast;
import totalcross.ui.gfx.Color;

public class ListUserView extends Container {
    private Label mainLabel = new Label("Usuários");

    private Container buttonRows = new Container();

    private ListContainer list;

    private Button backButton = new Button("Voltar");
    private Button removeCustomerButton = new Button("Remover");
    private Button addUserButton = new Button("+");

    private UserController userController = new UserController();
    private User[] users;
    private User user;

    public ListUserView(User user) {
        this.user = user;
    }

    @Override
    public void initUI() {

        setBackColor(Color.getRGB(242, 247, 244));

        mainLabel.setForeColor(Color.getRGB(46, 204, 113));
        mainLabel.setFont(mainLabel.getFont().adjustedBy(6));
        add(mainLabel, CENTER, TOP + 20);

        list = new ListContainer();
        add(list, LEFT + 20, AFTER + 10, FILL - 40, FILL - 130);
        loadUsers();

        buttonRows.setBackColor(Color.WHITE);
        add(buttonRows, LEFT, BOTTOM, FILL, 60);

        if (user.getAdmin() == 1) {
            removeCustomerButton.setBackColor(Color.getRGB(156, 39, 176));
            removeCustomerButton.setForeColor(Color.WHITE);
            buttonRows.add(
                removeCustomerButton,
                LEFT + 10,
                CENTER,
                (buttonRows.getWidth() / 2) - 15,
                45
            );
        }

        backButton.setBackColor(Color.getRGB(244, 67, 54));
        backButton.setForeColor(Color.WHITE);
        buttonRows.add(backButton, AFTER + 10, SAME, (buttonRows.getWidth() / 2) - 15, 45);

        if(user.getAdmin() == 1) {
            addUserButton.setBackColor(Color.getRGB(46, 204, 113));
            addUserButton.setForeColor(Color.WHITE);
            addUserButton.setFont(addUserButton.getFont().adjustedBy(10));
            add(addUserButton, RIGHT - 20, BOTTOM - 60, 60, 60);
        }


        backButton.addPressListener(e ->
            MainWindow.getMainWindow().swap(new HomeView(user))
        );

        removeCustomerButton.addPressListener(e ->
            removeUser()
        );

        addUserButton.addPressListener(e ->
            MainWindow.getMainWindow().swap(new UsersView(user))
        );
    }


    private void loadUsers() {
        try {
            users = userController.findAll();
            list.removeAll();
            for (User u : users) {
                if(!u.getEmail().equals("admin")) {
                    list.addContainer(new UserItem(u));
                }
            }
        } catch (Exception e) {
            Toast.show("Erro ao carregar clientes", 2000);
        }
    }

    private void removeUser() {

        int index = list.getSelectedIndex();

        if (index == -1) {
            Toast.show("Selecione um cliente", 2000);
            return;
        }

        try {
            userController.removeUser(users[index].getId());
            Toast.show("Cliente removido", 2000);
            MainWindow.getMainWindow().swap(new ListUserView(user));
        } catch (Exception e) {
            Toast.show("Falha ao remover cliente", 2000);
        }
    }
}
