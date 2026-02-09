package br.com.sovis.ordersmanager.view.forms;

import br.com.sovis.ordersmanager.controller.CustomerController;
import br.com.sovis.ordersmanager.controller.UserController;
import br.com.sovis.ordersmanager.model.Customer;
import br.com.sovis.ordersmanager.model.User;
import br.com.sovis.ordersmanager.model.UsersCustomer;
import br.com.sovis.ordersmanager.view.list.ListCustomersView;
import totalcross.ui.Button;
import totalcross.ui.ComboBox;
import totalcross.ui.Container;
import totalcross.ui.Label;
import totalcross.ui.MainWindow;
import totalcross.ui.Toast;
import totalcross.ui.font.Font;
import totalcross.ui.gfx.Color;

public class CustomerToUserView extends Container {
    private Label mainLabel = new Label("Usuários e Clientes");

    private ComboBox userBox;
    private ComboBox customersBox;

    private Button addProductButton = new Button("Adicionar Cliente");
    private Button saveButton = new Button("Salvar");
    private Button cancelButton = new Button("Cancelar");

    private Container footer = new Container();

    private UserController userController = new UserController();
    private CustomerController customerController = new CustomerController();

    private User[] users;
    private Customer[] customers;
    private UsersCustomer[] items = new UsersCustomer[100];
    private int itemCount = 0;

    private User user;

    int boxWidth = 300;
    int boxHeight = 50;
    int padding = 8;

    public CustomerToUserView(User user) {
        this.user = user;
    }

    @Override
    public void initUI() {

        setBackColor(Color.getRGB(242, 247, 244));

        mainLabel.setForeColor(Color.getRGB(46, 204, 113));
        mainLabel.setFont(Font.getFont(true, 22));
        add(mainLabel, CENTER, TOP + 25);

        try {
            Label userLabel = new Label("Usuários");
            userLabel.setForeColor(Color.getRGB(44, 62, 80));
            add(userLabel, LEFT + 40, AFTER + 35);

            users = userController.findAll();
            String[] names = new String[users.length];

            for (int i = 0; i < users.length; i++) {
                names[i] = users[i].getEmail();
            }

            userBox = new ComboBox(names);
            addCombo(userBox);

        } catch (Exception e) {
            Toast.show("Erro ao carregar clientes", 2000);
        }

        try {
            Label productLabel = new Label("Clientes");
            productLabel.setForeColor(Color.getRGB(44, 62, 80));
            add(productLabel, LEFT + 40, AFTER + 20);

            customers = customerController.findAll();
            String[] names = new String[customers.length + 1];
            names[0] = "Selecione o Produto";

            for (int i = 0; i < customers.length; i++) {
                names[i + 1] = customers[i].getName();
            }

            customersBox = new ComboBox(names);
            addCombo(customersBox);
        } catch (Exception e) {
            Toast.show("Erro ao carregar produtos", 2000);
        }

        addProductButton.setBackColor(Color.getRGB(156, 39, 176));
        addProductButton.setForeColor(Color.WHITE);
        add(addProductButton, CENTER, AFTER + 25, 220, 45);

        footer.setBackColor(Color.WHITE);
        add(footer, LEFT, BOTTOM, FILL, 70);

        saveButton.setBackColor(Color.getRGB(46, 204, 113));
        saveButton.setForeColor(Color.WHITE);
        footer.add(saveButton, LEFT + 10, CENTER, (footer.getWidth() / 2) - 15, 45);

        cancelButton.setBackColor(Color.getRGB(244, 67, 54));
        cancelButton.setForeColor(Color.WHITE);
        footer.add(cancelButton, AFTER + 10, SAME, (footer.getWidth() / 2) - 15, 45);

        addProductButton.addPressListener(e -> addCustomers());
        saveButton.addPressListener(e -> saveAssociation());
        cancelButton.addPressListener(e ->
            MainWindow.getMainWindow().swap(new ListCustomersView(user))
        );
    }

    private void addCombo(ComboBox box) {

        Container container = new Container();
        container.setBackColor(Color.WHITE);
        container.setBorderStyle(Container.BORDER_ROUNDED);
        container.borderColor = Color.getRGB(46, 204, 113);
        add(container, CENTER, AFTER + 6, boxWidth, boxHeight);

        container.add(box, LEFT + padding, TOP + padding,
                boxWidth - padding * 2, boxHeight - padding * 2);
    }

    private void addCustomers() {

        if (customersBox.getSelectedIndex() == 0) {
            Toast.show("Selecione um cliente", 2000);
            return;
        }

        Customer customer = customers[customersBox.getSelectedIndex() - 1];
        int userId = users[userBox.getSelectedIndex()].getId();

        UsersCustomer item = new UsersCustomer();
        item.setidCustomer(customer.getId());
        item.setIdUser(userId);
        try {
            if(userController.customerAlreadyAssocieated(item)) {
                Toast.show("Cliente já associado", 2000);
                return;
            }
            items[itemCount++] = item;
            customersBox.setSelectedIndex(0);

            Toast.show("Cliente adicionado", 2000);
        } catch (Exception e) {
            Toast.show("Erro ao adicionar cliente", 2000);
            System.err.println(e);
        }

    }

    private void saveAssociation() {
        if(itemCount == 0) {
            Toast.show("Adicione um cliente", 2000);
            return;
        }

        try {
            UsersCustomer[] finalItems = new UsersCustomer[itemCount];
            for (int i = 0; i < itemCount; i++) {
                finalItems[i] = items[i];
            }
            userController.createUserCustomerAssociation(finalItems);
            Toast.show("Clientes associados com sucesso", 2000);
            MainWindow.getMainWindow().swap(new ListCustomersView(user));
        } catch (Exception e) {
            System.err.println(e);
            Toast.show("Clientes não associados", 2000);
        }
    }
}
