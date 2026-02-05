package br.com.sovis.ordersmanager.view.items;

import br.com.sovis.ordersmanager.model.User;
import totalcross.ui.Container;
import totalcross.ui.Label;
import totalcross.ui.gfx.Color;

public class UserItem extends Container {
    private User user;

    public UserItem(User user) {
        this.user = user;
    }

    @Override
    public void initUI() {

        setBackColor(Color.WHITE);

        Label label = new Label(
            + user.getId() +
            " | " + user.getEmail()
        );

        label.setForeColor(Color.BLACK);

        add(label, LEFT + 10, CENTER);
    }

    public User getcustomer() {
        return user;
    }
}
