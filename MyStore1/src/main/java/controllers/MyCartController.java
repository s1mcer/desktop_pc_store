package controllers;

import java.io.IOException;
import java.util.List;

import application.HelloMsgs;
import application.MyListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import model.Order;
import model.Product;
import services.OrderService;
import services.ProductService;
import java.util.Date;
import utility.CurrentUser;
import utility.StringUtility;
import utility.Cart;

public class MyCartController extends Controller {

    @FXML
    private TextField addressField;

    @FXML
    private GridPane grid;

    @FXML
    private TextField nameField;

    @FXML
    private TextField phoneField;

	@FXML
    private ScrollPane scroll;

    @FXML
    private Label shippingLabel;

    @FXML
    private Label subtotalLabel;

    @FXML
    private Label totalLabel;
 
    private Date now = new Date();
    private double total;
    
    private MyListener myListener = new MyListener() {
        @Override
        public void onClickListener(Product product) {
            removeFromCart(product);
        }

		@Override
		public void onClickListener(Order order) {
			// TODO Auto-generated method stub
			
		}
    };
    
    @FXML
    public void initialize() {
        updateGrid();
        setLabels();
    }

    @FXML
    void goToMain(ActionEvent event) throws IOException {
        switchScene(event, "/resources_view/MainPage.fxml", StringUtility.capitalizeWords(HelloMsgs.randomHelloMsg().getHelloMsg()));
    }

    @FXML
	void goToMyOrders(ActionEvent event) {
		try {
			switchScene(event, "/resources_view/ViewOrders.fxml", "Your Orders");
		} catch (IOException e) {
			warningAlert(event, e.getMessage());
		}
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

    void setLabels() {
        List<Product> productsInCart = Cart.getProductsInCart();
        double subtotal = productsInCart.stream().mapToDouble(Product::getPrice).sum();
        double shipping = subtotal > 2000 ? 0 : 200;
        setTotal(subtotal + shipping);

        subtotalLabel.setText(String.format("$%.2f", subtotal));
        shippingLabel.setText(String.format("$%.2f", shipping));
        totalLabel.setText(String.format("$%.2f", getTotal()));
    }

    @FXML
    void placeOrder(ActionEvent event) {
        OrderService orderService = new OrderService();
        ProductService productService = new ProductService();
        List<Product> productsInCart = Cart.getProductsInCart();
        try {
        	orderService.validateFields(nameField.getText(), phoneField.getText(), addressField.getText());
            productService.checkIfListIsEmpty(productsInCart);
            StringBuilder list = new StringBuilder("");
            for (int i = 0; i < productsInCart.size(); i++) {
                list.append(productsInCart.get(i).getName());
                if (i < productsInCart.size() - 1) {
                    list.append(", ");
                }
            }
            orderService.addOrder(CurrentUser.getUserid(), nameField.getText(), phoneField.getText(), addressField.getText(), getTotal(), now, list.toString());
            informationAlert(event, "Your order has been placed successfully");
            Cart.clearCart();
            initialize();
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            System.out.println("Error: " + errorMessage);
            warningAlert(event, errorMessage);
        }
    }

    private void removeFromCart(Product product) {
        Cart.removeProduct(product);
        updateGrid();
        setLabels();
    }

    private void updateGrid() {
        List<Product> productsInCart = Cart.getProductsInCart();
        grid.getChildren().clear();
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < productsInCart.size(); i++) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/resources_view/CheckoutItem.fxml"));
                AnchorPane anchorPane = loader.load();

                ItemController itemController = loader.getController();
                itemController.setData(productsInCart.get(i), myListener);

                if (column == 3) {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	public TextField getAddressField() {
		return addressField;
	}

	public void setAddressField(TextField addressField) {
		this.addressField = addressField;
	}

	public TextField getNameField() {
		return nameField;
	}

	public void setNameField(TextField nameField) {
		this.nameField = nameField;
	}

	public TextField getPhoneField() {
		return phoneField;
	}

	public void setPhoneField(TextField phoneField) {
		this.phoneField = phoneField;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
    
    
}
