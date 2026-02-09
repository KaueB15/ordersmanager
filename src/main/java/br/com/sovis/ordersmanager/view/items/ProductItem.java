package br.com.sovis.ordersmanager.view.items;

import br.com.sovis.ordersmanager.model.Product;
import totalcross.ui.Label;
import totalcross.ui.ScrollContainer;
import totalcross.ui.gfx.Color;

public class ProductItem extends ScrollContainer {

    private Label productName;
    private Label productId;
    private Label productDescription;
    private Label productPrice;

    private Product product;

    public ProductItem(Product product) {
        super(false);
        this.product = product;
    }

    @Override
    public void initUI() {

        setBackColor(Color.WHITE);

        productId = new Label("" + product.getId());
        productId.setForeColor(Color.BLACK);
        add(productId, LEFT + 10, TOP, FILL, PREFERRED);

        productName = new Label(product.getName());
        productName.setForeColor(Color.BLACK);
        add(productName, LEFT + 10, AFTER + 5);

        productDescription = new Label(product.getDescription());
        productDescription.setForeColor(Color.BLACK);
        add(productDescription, LEFT + 10, AFTER + 5);

        productPrice = new Label("R$ " + String.format("%.2f", product.getPrice()));
        productPrice.setForeColor(Color.BLACK);
        add(productPrice, LEFT + 10, AFTER + 5);

        // Label label = new Label(
        //     + product.getId() +
        //     " | " + product.getName() +
        //     " | R$ " + product.getPrice()
        // );

        // label.setForeColor(Color.BLACK);

        // add(label, LEFT + 10, CENTER);
    }

    public Product getproduct() {
        return product;
    }

}
