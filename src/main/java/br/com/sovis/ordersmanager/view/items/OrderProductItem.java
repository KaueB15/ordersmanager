package br.com.sovis.ordersmanager.view.items;

import br.com.sovis.ordersmanager.model.ProductItem;
import totalcross.ui.Label;
import totalcross.ui.ScrollContainer;
import totalcross.ui.gfx.Color;

public class OrderProductItem extends ScrollContainer {

    private Label productPrice;
    private Label productName;
    private Label productQuantity;
    private Label productDescription;

    private ProductItem item;

    public OrderProductItem(ProductItem item) {
        super(false);
        this.item = item;
    }

    @Override
    public void initUI() {

        setBackColor(Color.getRGB(255, 255, 255));

        productName = new Label(item.getProductName());
        productName.setForeColor(Color.BLACK);
        add(productName, LEFT + 10, AFTER + 5, FILL, PREFERRED);

        productDescription = new Label(item.getDescription());
        productDescription.setForeColor(Color.BLACK);
        add(productDescription, LEFT + 10, AFTER + 5);

        productQuantity = new Label("Quantidade: " + item.getQuantity());
        productQuantity.setForeColor(Color.BLACK);
        add(productQuantity, LEFT + 10, AFTER + 5);

        productPrice = new Label("Total - R$ " + String.format("%.2f", item.getPrice()));
        productPrice.setForeColor(Color.BLACK);
        add(productPrice, LEFT + 10, AFTER + 5);
    }

    public ProductItem getItem() {
        return item;
    }
}
