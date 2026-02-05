package br.com.sovis.ordersmanager.view.items;

import br.com.sovis.ordersmanager.model.Product;
import totalcross.ui.Container;
import totalcross.ui.Label;
import totalcross.ui.gfx.Color;

public class ProductItem extends Container {

    private Product product;

    public ProductItem(Product product) {
        this.product = product;
    }

    @Override
    public void initUI() {

        setBackColor(Color.WHITE);

        Label label = new Label(
            + product.getId() +
            " | " + product.getName() +
            " | R$ " + product.getPrice()
        );

        label.setForeColor(Color.BLACK);

        add(label, LEFT + 10, CENTER);
    }

    public Product getproduct() {
        return product;
    }

}
