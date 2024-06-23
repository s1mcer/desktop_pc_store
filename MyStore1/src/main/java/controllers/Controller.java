package controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Controller {

	public void warningAlert(ActionEvent event, String errorMessage) {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle("Warning Dialog");
		alert.setContentText(errorMessage);
		alert.setHeaderText("Warning alert");
		alert.getDialogPane().setPrefSize(400, 240);
		alert.showAndWait();
	}

	void switchScene(ActionEvent event, String resourcePath, String title) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource(resourcePath));
		Scene scene = new Scene(root);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.sizeToScene();
		stage.setTitle(title);
		Image icon = new Image("/images/desktop-solid.png");
		stage.getIcons().add(icon);
		stage.setResizable(false);
		stage.show();
	}

	public void informationAlert(ActionEvent event, String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		alert.setContentText(message);
		alert.setHeaderText("Information alert");
		alert.getDialogPane().setPrefSize(400, 240);
		alert.showAndWait();
	}
	
}
