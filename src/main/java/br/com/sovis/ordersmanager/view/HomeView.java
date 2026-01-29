package br.com.sovis.ordersmanager.view;

import totalcross.ui.Button;
import totalcross.ui.Container;
import totalcross.ui.Label;
import totalcross.ui.MainWindow;
import totalcross.ui.event.DragEvent;
import totalcross.ui.event.PenEvent;
import totalcross.ui.event.PenListener;
import totalcross.ui.gfx.Color;
import totalcross.ui.font.Font;

public class HomeView extends Container {

    private Label titleLabel = new Label("Bem-vindo!");
    private Button menuButton = new Button("☰");

    private Button customerButton = new Button("Clientes");
    private Button productsButton = new Button("Produtos");
    private Button ordersButton = new Button("Pedidos");
    private Button exitButton = new Button("Sair");

    private Container sideMenu = new Container();
    private boolean menuVisible = false;

    @Override
    public void initUI() {

        setBackColor(Color.getRGB(240, 248, 243));

        menuButton.setBackColor(Color.getRGB(39, 174, 96));
        menuButton.setForeColor(Color.WHITE);
        menuButton.setFont(Font.getFont(true, 24));
        add(menuButton, LEFT + 10, TOP + 10, 60, 60);

        menuButton.addPressListener(event -> toggleMenu());

        titleLabel.setForeColor(Color.getRGB(39, 174, 96));
        titleLabel.setFont(Font.getFont(true, 28));
        add(titleLabel, CENTER, TOP + 50);

        sideMenu.setBackColor(Color.getRGB(39, 174, 96));
        sideMenu.setVisible(false);
        add(sideMenu, LEFT, TOP, 200, FILL);

        addMenuButton(customerButton);
        addMenuButton(productsButton);
        addMenuButton(ordersButton);
        addMenuButton(exitButton);

        customerButton.addPressListener(event -> MainWindow.getMainWindow().swap(new ListCustomersView()));
        productsButton.addPressListener(event -> MainWindow.getMainWindow().swap(new ListProductsView()));
        ordersButton.addPressListener(event -> MainWindow.getMainWindow().swap(new ListOrderView()));
        exitButton.addPressListener(event -> MainWindow.getMainWindow().swap(new LoginView()));

        addPenListener(new PenListener() {
            @Override
            public void penUp(PenEvent e) {
                if (menuVisible) {
                    if (e.x > sideMenu.getWidth()) {
                        toggleMenu();
                    }
                }
            }

            @Override
            public void penDown(PenEvent arg0) {}

            @Override
            public void penDrag(DragEvent arg0) {}

            @Override
            public void penDragEnd(DragEvent arg0) {}

            @Override
            public void penDragStart(DragEvent arg0) {}

        });
    }

    private void toggleMenu() {
        menuVisible = !menuVisible;
        sideMenu.setVisible(menuVisible);
        sideMenu.repaintNow();
    }

    private void addMenuButton(Button button) {
        button.setBackColor(Color.getRGB(39, 174, 96));
        button.setForeColor(Color.WHITE);
        button.setFont(Font.getFont(true, 16));
        sideMenu.add(button, LEFT + 20, AFTER + 20, FILL - 40, 40);
    }
}
