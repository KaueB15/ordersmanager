package br.com.sovis.ordersmanager.view;

import br.com.sovis.ordersmanager.controller.CustomerController;
import totalcross.ui.Button;
import totalcross.ui.ComboBox;
import totalcross.ui.Container;
import totalcross.ui.Label;
import totalcross.ui.MainWindow;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.PressListener;
import totalcross.ui.gfx.Color;

public class OrderView extends Container {

    private Label customerLabel = new Label("Clientes");
    private CustomerController customerController = new CustomerController();
    private ComboBox customersBox;
    private Button backButton = new Button("Voltar");

    public void initUI() {
        try {
            add(customerLabel, LEFT + 20, TOP + 20);
            String[] customersNames = customerController.getCustomersNames();
            customersBox = new ComboBox(customersNames);
            customersBox.setSelectedIndex(0);
            add(customersBox, LEFT + 20, AFTER + 2, FILL - 40, PREFERRED);
        } catch (Exception e) {
            e.printStackTrace();
        }

        backButton.setBackColor(Color.RED);
        backButton.setForeColor(Color.WHITE);
        add(backButton, CENTER, AFTER + 10);

        backButton.addPressListener(new PressListener() {
            
            public void controlPressed(ControlEvent e) {
                
                MainWindow.getMainWindow().swap(new HomeView());

            }

        });

    }

}
