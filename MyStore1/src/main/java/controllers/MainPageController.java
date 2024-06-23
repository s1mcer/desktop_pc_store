package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import application.HelloMsgs;
import application.MyListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import model.Order;
import model.Product;
import services.ProductService;
import utility.Cart;
import utility.StringUtility;

public class MainPageController extends Controller implements Initializable {

	@FXML
	private ImageView componentImage;

	@FXML
	private Label componentLabel;

	@FXML
	private Label componentPrice;

	@FXML
	private TextField searchField;

	@FXML
	private GridPane grid;

	@FXML
	private ScrollPane scroll;

	@FXML
	private VBox chosenItemCard;

	private List<Product> products = new ArrayList<>();
	private ProductService productService = new ProductService();
	private MyListener myListener;

	private Product chosenProduct;

	private List<Product> getData() {
		return productService.getAllProducts();
	}

	@FXML
	void addToCart(ActionEvent event) {
		if (chosenProduct != null) {
			Cart.addProduct(chosenProduct);
			informationAlert(event, "Product successfully added to cart!");
		}
	}

	@FXML
	void goToCart(ActionEvent event) throws IOException {
		switchScene(event, "/resources_view/MyCart.fxml","My Cart");
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

	@FXML
	void searchButton(ActionEvent event) {
		performSearch();
	}

	@FXML
	private void onEnterPressed(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			performSearch();
		}
	}

	@FXML
	void sortAsc(ActionEvent event) {
		Collections.sort(products, Comparator.comparingDouble(Product::getPrice));
		performSearch();
	}

	@FXML
	void sortDesc(ActionEvent event) {
		Collections.sort(products, Comparator.comparingDouble(Product::getPrice).reversed());
		performSearch();
	}

	private void performSearch() {
		String searchText = searchField.getText();
		List<Product> filteredProducts = filterProducts(searchText);
		updateGrid(filteredProducts);
	}

	private List<Product> filterProducts(String searchText) {
		if (searchText == null || searchText.isEmpty()) {
			return products;
		}
		return products.stream().filter(product -> product.getName().toLowerCase().contains(searchText.toLowerCase()))
				.collect(Collectors.toList());
	}

	private void updateGrid(List<Product> productsToDisplay) {
		grid.getChildren().clear();
		int column = 0;
		int row = 1;
		try {
			for (int i = 0; i < productsToDisplay.size(); i++) {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/resources_view/Item.fxml"));
				AnchorPane anchorPane = loader.load();

				ItemController itemController = loader.getController();
				itemController.setData(productsToDisplay.get(i), myListener);

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

	private void setChosenProduct(Product product) {
		chosenProduct = product;
		componentLabel.setText(product.getName());
		componentPrice.setText("$" + product.getPrice());
		Image image = new Image(getClass().getResourceAsStream(product.getPath()));
		componentImage.setImage(image);
	}

	@Override
	public void initialize(URL url, ResourceBundle resources) {
		products.addAll(getData());
		Collections.shuffle(products);
		if (products.size() > 0) {
			setChosenProduct(products.get(0));
			myListener = new MyListener() {
				@Override
				public void onClickListener(Product product) {
					setChosenProduct(product);
				}

				@Override
				public void onClickListener(Order order) {
					// TODO Auto-generated method stub
				}
			};
		}
		updateGrid(products);

		// Add key event handler to search field
		searchField.setOnKeyPressed(this::onEnterPressed);
	}

}
