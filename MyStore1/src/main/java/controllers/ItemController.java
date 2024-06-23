package controllers;

import application.MyListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.Product;

public class ItemController {

	@FXML
	private ImageView itemImage;

	@FXML
	private Label itemNameLabel;

	@FXML
	private Label itemPriceLabel;

	@FXML
	private void click(MouseEvent event) {
		//if (myListener != null)
			myListener.onClickListener(product);
	}

	private Product product;
	private MyListener myListener;

	public void setData(Product product, MyListener myListener) {
		this.product = product;
		this.myListener = myListener;
		itemNameLabel.setText(product.getName());
		itemPriceLabel.setText("$" + product.getPrice());
		Image image = new Image(getClass().getResourceAsStream(product.getPath()));
		itemImage.setImage(image);

	}
}
