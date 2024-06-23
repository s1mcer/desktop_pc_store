package controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import services.UserService;

public class SignupController extends Controller implements LoginSignupInterface {

	@FXML
	private TextField usernameField;

	@FXML
	private TextField emailField;

	@FXML
	private PasswordField passwordField;

	@FXML
	private PasswordField repeatPasswordField;

	public void signUpButtonOnAction(ActionEvent event) {

		UserService userService = new UserService();
		try {
			userService.validateFields(usernameField.getText(), emailField.getText(), passwordField.getText(),
					repeatPasswordField.getText());
			userService.checkPasswordMatch(passwordField.getText(), repeatPasswordField.getText());
			userService.checkUserExists(usernameField.getText(), passwordField.getText());
			userService.addUser(usernameField.getText(), emailField.getText(), passwordField.getText());
			informationAlert(event, "User " + usernameField.getText() + " registered successfully!");
			switchScene(event, "/resources_view/LoginScreen.fxml", "Log In");
		} catch (Exception e) {
			String errorMessage = e.getMessage();
			System.out.println("Error: " + errorMessage);
			warningAlert(event, errorMessage);
			clearFormFields();
		}

	}

	private void clearFormFields() {
		usernameField.clear();
		emailField.clear();
		passwordField.clear();
		repeatPasswordField.clear();
	}

	public void goToLogIn(ActionEvent event) throws IOException {
		switchScene(event, "/resources_view/LoginScreen.fxml", "Log In");
	}

}
