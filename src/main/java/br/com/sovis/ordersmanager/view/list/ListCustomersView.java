package br.com.sovis.ordersmanager.view.list;

import br.com.sovis.ordersmanager.controller.CustomerController;
import br.com.sovis.ordersmanager.model.Customer;
import br.com.sovis.ordersmanager.model.User;
import br.com.sovis.ordersmanager.view.HomeView;
import br.com.sovis.ordersmanager.view.forms.CustomerToUserView;
import br.com.sovis.ordersmanager.view.forms.CustomerView;
import br.com.sovis.ordersmanager.view.items.CustomerItem;
import totalcross.ui.*;
import totalcross.ui.gfx.Color;
import totalcross.ui.image.Image;

public class ListCustomersView extends Container {

    private Label mainLabel = new Label("Clientes");

    private ListContainer list;

    private Button backButton;
    private Button removeCustomerButton;
    private Button addCustomerButton = new Button("+");
    private Button assButton;

    private CustomerController customerController = new CustomerController();
    private Customer[] customers;
    private User user;

    public ListCustomersView(User user) {
        this.user = user;
    }

    @Override
    public void initUI() {

        setBackColor(Color.getRGB(242, 247, 244));

        mainLabel.setForeColor(Color.getRGB(46, 204, 113));
        mainLabel.setFont(mainLabel.getFont().adjustedBy(6));
        add(mainLabel, CENTER, TOP + 20);

        list = new ListContainer();
        add(list, LEFT + 20, AFTER + 10, FILL - 40, FILL - 80);
        loadCustomers();

        try {

            if (user.getAdmin() == 1) {
                removeCustomerButton = new Button(new Image("delete.png").getScaledInstance(20, 20));
                removeCustomerButton.setBackColor(Color.getRGB(156, 39, 176));
                removeCustomerButton.setForeColor(Color.WHITE);
                add(removeCustomerButton, RIGHT - 190, BOTTOM - 10, 60, 60);

                assButton = new Button(new Image("customers.png").getScaledInstance(20, 20));
                assButton.setBackColor(Color.getRGB(46, 204, 113));
                assButton.setForeColor(Color.WHITE);
                assButton.setFont(assButton.getFont().adjustedBy(10));
                add(assButton, RIGHT - 130, BOTTOM - 10, 60, 60);
    
                addCustomerButton.setBackColor(Color.getRGB(46, 204, 113));
                addCustomerButton.setForeColor(Color.WHITE);
                addCustomerButton.setFont(addCustomerButton.getFont().adjustedBy(10));
                add(addCustomerButton, RIGHT - 70, BOTTOM - 10, 60, 60);
            }

            backButton = new Button(new Image("home.png").getScaledInstance(20, 20));
            backButton.setBackColor(Color.getRGB(244, 67, 54));
            backButton.setForeColor(Color.WHITE);
            add(backButton, RIGHT - 10, BOTTOM - 10, 60, 60);
        } catch(Exception e) {
            System.out.println(e);
        }

        backButton.addPressListener(e ->
            MainWindow.getMainWindow().swap(new HomeView(user))
        );

        assButton.addPressListener(e -> {
            MainWindow.getMainWindow().swap(new CustomerToUserView(user));
        });

        removeCustomerButton.addPressListener(e ->
            removeCustomer()
        );

        addCustomerButton.addPressListener(e ->
            MainWindow.getMainWindow().swap(new CustomerView(user))
        );
    }


    private void loadCustomers() {
        try {
            if(user.getAdmin() == 1) {
                customers = customerController.findAll();
            } else {
                customers = customerController.findByUserId(user.getId());
            }

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
            if(customerController.customerAlreadyUsed(customers[index].getId())) {
                Toast.show("Cliente sendo utilizado", 2000);
                return;
            }
            customerController.removerCustomer(customers[index].getId());
            Toast.show("Cliente removido", 2000);
            MainWindow.getMainWindow().swap(new ListCustomersView(user));
        } catch (Exception e) {
            Toast.show("Falha ao remover cliente", 2000);
        }
    }
}
