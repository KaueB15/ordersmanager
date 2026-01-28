package br.com.sovis.ordersmanager.view.items;

import br.com.sovis.ordersmanager.model.Customer;
import totalcross.ui.Container;
import totalcross.ui.Label;
import totalcross.ui.gfx.Color;

public class CustomerItem extends Container {

    private Customer customer;

    public CustomerItem(Customer customer) {
        this.customer = customer;
    }

    @Override
    public void initUI() {

        setBackColor(Color.WHITE);

        Label label = new Label(
            + customer.getId() +
            " | " + customer.getName()
        );

        label.setForeColor(Color.BLACK);

        add(label, LEFT + 10, CENTER);
    }

    public Customer getcustomer() {
        return customer;
    }

}
