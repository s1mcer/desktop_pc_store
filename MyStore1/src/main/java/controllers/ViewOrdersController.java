package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.HelloMsgs;
import application.MyListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import model.Order;
import model.Product;
import services.OrderService;
import utility.Cart;
import utility.CurrentUser;
import utility.StringUtility;

public class ViewOrdersController extends Controller implements Initializable {

	@FXML
	private Label addressLabel;

	@FXML
	private Label dateLabel;

	@FXML
	private GridPane grid;

	@FXML
	private Label idLabel;

	@FXML
	private ScrollPane scroll;

	@FXML
	private Label totalLabel;

	private OrderService orderService = new OrderService();
	private MyListener myListener;
	private List<Order> orders = new ArrayList<>();

	public List<Order> getMyOrders() {
		return orderService.getAllOrdersFromTheSameUser(CurrentUser.getUserid());
	}

	@FXML
	void goToCart(ActionEvent event) throws IOException {
		switchScene(event, "/resources_view/MyCart.fxml", "My Cart");
	}

	private void setOrderDetails(Order order) {
		addressLabel.setText(order.getDeliveryAddress());
		dateLabel.setText(StringUtility.formatDate(order.getOrderDate()));
		idLabel.setText(order.getOrderId() + "");
		totalLabel.setText("$" + order.getOrderValue());
	}

	@FXML
	void goToMain(ActionEvent event) throws IOException {
		switchScene(event, "/resources_view/MainPage.fxml",
				StringUtility.capitalizeWords(HelloMsgs.randomHelloMsg().getHelloMsg()));
	}

	@FXML
	void logOut(ActionEvent event) {
		try {
			switchScene(event, "/resources_view/LoginScreen.fxml", "Log In");
			Cart.clearCart();
		} catch (IOException e) {
			warningAlert(event, e.getMessage());
		}
	}

	@FXML
	public void initialize() {
		updateGrid();
	}

	private void updateGrid() {
	    List<Order> myOrders = getMyOrders();
	    int column = 0;
	    int row = 1;
	    try {
	        for (int i = 0; i < myOrders.size(); i++) {
	            FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(getClass().getResource("/resources_view/OrderCard.fxml"));
	            AnchorPane anchorPane;
	            try {
	                anchorPane = loader.load();
	            } catch (ClassCastException e) {
	                throw new IllegalStateException("Check the root element of OrderCard.fxml. It must be an AnchorPane.", e);
	            }

	            OrderCardController orderCardController = loader.getController();
	            orderCardController.setData(myOrders.get(i), myListener);

	            if (column == 1) {
	                column = 0;
	                row++;
	            }

	            grid.add(anchorPane, column++, row); // (child, column, row)
	            // set grid width
	            grid.setMinWidth(Region.USE_COMPUTED_SIZE);
	            grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
	            grid.setMaxWidth(Region.USE_PREF_SIZE);

	            // set grid height
	            grid.setMinHeight(Region.USE_COMPUTED_SIZE);
	            grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
	            grid.setMaxHeight(Region.USE_PREF_SIZE);

	            GridPane.setMargin(anchorPane, new Insets(10));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}


	@Override
	public void initialize(URL url, ResourceBundle resources) {
		orders.addAll(getMyOrders());
		if (orders.size() > 0) {
			setOrderDetails(orders.get(0));
			myListener = new MyListener() {
				@Override
				public void onClickListener(Product product) {
					// setChosenProduct(product);
				}

				@Override
				public void onClickListener(Order order) {
					setOrderDetails(order);
				}
			};
		}
		updateGrid();
	}

}