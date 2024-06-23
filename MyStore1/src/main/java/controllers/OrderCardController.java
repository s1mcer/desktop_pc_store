package controllers;

import application.MyException;
import application.MyListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.Order;
import model.Product;
import services.ProductService;
import utility.StringUtility;

public class OrderCardController {

    @FXML
    private ImageView firstProductImage;

    @FXML
    private Label idLabel;

    @FXML
    private Label productListLabel;

    @FXML
	private void click(MouseEvent event) {
			myListener.onClickListener(order);
	}

	private Order order;
	private MyListener myListener;

	public void setData(Order order, MyListener myListener) throws MyException {
	    this.order = order;
	    this.myListener = myListener;
	    idLabel.setText(order.getOrderId() + "");
	    String string = order.getOrderedProductList();
	    productListLabel.setText(string);
	    string = StringUtility.getFirstWord(string);
	    System.out.println("Product name to search: " + string); // Logging
	    ProductService ps = new ProductService();
	    try {
	        Product p = ps.findProduct(string);
	        Image image = new Image(getClass().getResourceAsStream(p.getPath()));
	        firstProductImage.setImage(image);
	    } catch (MyException e) {
	        System.err.println(e.getMessage());
	        // Handle the exception gracefully
	        firstProductImage.setImage(null); // Reset the image if product is not found
	    }
	}

}
