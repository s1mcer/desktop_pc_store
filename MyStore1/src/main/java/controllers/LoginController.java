package controllers;

import application.HelloMsgs;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import services.UserService;

public class LoginController extends Controller implements LoginSignupInterface {

	@FXML
	private TextField usernameField;

	@FXML
	private PasswordField passwordField;


	public void logInButtonOnAction(ActionEvent event) {
		UserService userService = new UserService();
		try {
			if (userService.findUser(usernameField.getText(), passwordField.getText()) != null) {
				switchScene(event, "/resources_view/MainPage.fxml", HelloMsgs.randomHelloMsg().getHelloMsg());
			}
		} catch (Exception e) {
			String errorMessage = e.getMessage();
			System.out.println("Error: " + errorMessage);
			warningAlert(event, errorMessage);
			clearFormFields();
		}
	}

	public void goToSignUp(ActionEvent event) throws Exception {
		switchScene(event, "/resources_view/SignupScreen.fxml", "Sign Up");

	}

	private void clearFormFields() {
		usernameField.clear();
		passwordField.clear();

	}

}