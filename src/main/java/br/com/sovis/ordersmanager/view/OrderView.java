package br.com.sovis.ordersmanager.view;

import br.com.sovis.ordersmanager.controller.CustomerController;
import totalcross.ui.ComboBox;
import totalcross.ui.Container;

public class OrderView extends Container {

    private CustomerController customerController = new CustomerController();
    private ComboBox customersBox;

    public void initUI() {
        try {
            String[] customersNames = customerController.getCustomersNames();
            customersBox = new ComboBox(customersNames);
            add(customersBox, CENTER, CENTER);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
