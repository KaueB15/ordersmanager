package br.com.sovis.ordersmanager.view;

import br.com.sovis.ordersmanager.controller.CustomerController;
import br.com.sovis.ordersmanager.model.Customer;
import br.com.sovis.ordersmanager.view.items.CustomerItem;
import totalcross.ui.*;
import totalcross.ui.gfx.Color;

public class ListCustomersView extends Container {

    private Label mainLabel = new Label("Clientes");

    private Container buttonRows = new Container();

    private ListContainer list;

    private Button backButton = new Button("Voltar");
    private Button removeCustomerButton = new Button("Remover");
    private Button addCustomerButton = new Button("+");

    private CustomerController customerController = new CustomerController();
    private Customer[] customers;

    @Override
    public void initUI() {

        setBackColor(Color.getRGB(242, 247, 244));

        mainLabel.setForeColor(Color.getRGB(46, 204, 113));
        mainLabel.setFont(mainLabel.getFont().adjustedBy(6));
        add(mainLabel, CENTER, TOP + 20);

        list = new ListContainer();
        add(list, LEFT + 20, AFTER + 10, FILL - 40, FILL - 130);
        loadCustomers();

        buttonRows.setBackColor(Color.WHITE);
        add(buttonRows, LEFT, BOTTOM, FILL, 60);

        removeCustomerButton.setBackColor(Color.getRGB(156, 39, 176));
        removeCustomerButton.setForeColor(Color.WHITE);
        buttonRows.add(
            removeCustomerButton,
            LEFT + 10,
            CENTER,
            (buttonRows.getWidth() / 2) - 15,
            45
        );

        backButton.setBackColor(Color.getRGB(244, 67, 54));
        backButton.setForeColor(Color.WHITE);
        buttonRows.add(backButton, AFTER + 10, SAME, (buttonRows.getWidth() / 2) - 15, 45);

        addCustomerButton.setBackColor(Color.getRGB(46, 204, 113));
        addCustomerButton.setForeColor(Color.WHITE);
        addCustomerButton.setFont(addCustomerButton.getFont().adjustedBy(10));

        add(addCustomerButton, RIGHT - 20, BOTTOM - 60, 60, 60);

        backButton.addPressListener(e ->
            MainWindow.getMainWindow().swap(new HomeView())
        );

        removeCustomerButton.addPressListener(e ->
            removeCustomer()
        );

        addCustomerButton.addPressListener(e ->
            MainWindow.getMainWindow().swap(new CustomerView())
        );
    }


    private void loadCustomers() {
        try {
            customers = customerController.findAll();
            list.removeAll();
            for (Customer c : customers) {
                list.addContainer(new CustomerItem(c));
            }
        } catch (Exception e) {
            Toast.show("Erro ao carregar clientes", 2000);
        }
    }

    private void removeCustomer() {

        int index = list.getSelectedIndex();

        if (index == -1) {
            Toast.show("Selecione um cliente", 2000);
            return;
        }

        try {
            customerController.removerCustomer(customers[index].getId());
            Toast.show("Cliente removido", 2000);
            MainWindow.getMainWindow().swap(new ListCustomersView());
        } catch (Exception e) {
            Toast.show("Falha ao remover cliente", 2000);
        }
    }
}
