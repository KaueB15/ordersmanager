package br.com.sovis.ordersmanager.view.list;

import br.com.sovis.ordersmanager.controller.CustomerController;
import br.com.sovis.ordersmanager.controller.UserController;
import br.com.sovis.ordersmanager.model.Customer;
import br.com.sovis.ordersmanager.model.User;
import br.com.sovis.ordersmanager.view.items.CustomerItem;
import totalcross.ui.Button;
import totalcross.ui.Container;
import totalcross.ui.Label;
import totalcross.ui.ListContainer;
import totalcross.ui.MainWindow;
import totalcross.ui.Toast;
import totalcross.ui.gfx.Color;

public class CustomerUserInfoView extends Container {
    private Label mainLabel = new Label("Clientes do Usuário");
    private ListContainer list;
    private Container bottomBar = new Container();
    private Button backButton = new Button("Voltar");
    private Button cancelCustomerButton = new Button("Retirar Cliente");

    private int userId;
    private CustomerController customerController = new CustomerController();
    private UserController userController = new UserController();
    private Customer[] customerItems;
    private User user;

    public CustomerUserInfoView(User user, int userId) {
        this.userId = userId;
        this.user = user;
    }

    @Override
    public void initUI() {

        setBackColor(Color.getRGB(240, 248, 243));

        mainLabel.setForeColor(Color.getRGB(39, 174, 96));
        add(mainLabel, CENTER, TOP + 20);

        list = new ListContainer();
        add(list, LEFT + 20, AFTER + 15, FILL - 40, FILL - 130);
        loadCustomers();

        add(bottomBar, LEFT, BOTTOM, FILL, 70);
        bottomBar.setBackColor(Color.getRGB(255, 255, 255));

        cancelCustomerButton.setBackColor(Color.getRGB(39, 174, 96));
        cancelCustomerButton.setForeColor(Color.WHITE);
        bottomBar.add(cancelCustomerButton, LEFT + 10, CENTER, bottomBar.getWidth() / 2 - 15, 45);

        backButton.setBackColor(Color.getRGB(231, 76, 60));
        backButton.setForeColor(Color.WHITE);
        bottomBar.add(backButton, RIGHT - 10, CENTER, bottomBar.getWidth() / 2 - 15, 45);

        backButton.addPressListener(e ->
            MainWindow.getMainWindow().swap(new ListUserView(user))
        );

        cancelCustomerButton.addPressListener(e ->
            deleteCustomer()
        );
    }

    private void loadCustomers() {

        try {
            customerItems = customerController.findByUserId(userId);
            list.removeAll();

            for (int i = 0; i < customerItems.length; i++) {
                list.addContainer(new CustomerItem(customerItems[i]));
            }

        } catch (Exception e) {
            Toast.show("Erro ao carregar clientes", 2000);
        }
    }

    private void deleteCustomer() {

        int selected = list.getSelectedIndex();

        if (selected == -1) {
            Toast.show("Selecione um cliente", 2000);
            return;
        }

        try {
            int customerId = customerItems[selected].getId();
            userController.deleteCustomerFromUser(userId, customerId);
            MainWindow.getMainWindow().swap(new CustomerUserInfoView(user, userId));
            Toast.show("Cliente removido", 2000);
        } catch (Exception e) {
            Toast.show("Falha ao remover cliente", 2000);
        }
    }
}
