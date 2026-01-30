package br.com.sovis.ordersmanager.view.items;

import br.com.sovis.ordersmanager.model.ProductItem;
import totalcross.ui.Container;
import totalcross.ui.Label;
import totalcross.ui.gfx.Color;

public class OrderProductItem extends Container {

    private ProductItem item;

    public OrderProductItem(ProductItem item) {
        this.item = item;
    }

    @Override
    public void initUI() {

        setBackColor(Color.getRGB(255, 255, 255));

        Label label = new Label(
                item.getProductName() +
                " | " + item.getQuantity() +
                " | Total: " + item.getPrice()
        );

        label.setForeColor(Color.getRGB(44, 62, 80));
        add(label, LEFT + 12, CENTER);
    }

    public ProductItem getItem() {
        return item;
    }
}
