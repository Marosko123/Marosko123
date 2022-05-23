package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * Class that controls the flow of the registration
 * @author Maroš
 */
public class RegistrationController implements Initializable {
	@FXML
	TextField usernameTextField;
	@FXML
	PasswordField passwordField;
	@FXML
	TextField favYT;
	@FXML
	Button loginButton;
	@FXML
	Button checkConnectionButton;
	@FXML
	Button registerButton;
	@FXML
    Text wrongInputText;
	@FXML
	Label connectionLabel;
	@FXML
	ImageView connectImg;
	@FXML
	Slider moneySlider;
	
	Parent root;
	Stage stage;
	Scene scene;
	
	/**
	 * function that says if the user pressed any UI element
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		/**
		 * if register button is pressed differ if the given register information are correct and then register a new user
		 */
		registerButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("Pressed register button");
				if (!usernameTextField.getText().trim().isEmpty() && !passwordField.getText().trim().isEmpty()) {
					try {
						checkPassword(passwordField.getText());
						DBUtils.signUpUser(event, usernameTextField.getText(), passwordField.getText(), (int) moneySlider.getValue(), 0);
					} catch (IncorrectPasswordFormatException e) {
						System.out.println(e.getMessage());
						Alert alert = new Alert(Alert.AlertType.WARNING) ;
						alert.setContentText(e.getMessage());
						alert.show();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}else {
					System.out.println("Please fill in all information");
					//wrongInputText.setText("Please fill in all information");
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setContentText("Please  fill in all information to sign up!");
					alert.show();
				}
			}
		});
		
		/**
		 * opens the login menu
		 */
		loginButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("Navrat k loginu z registracie");
				DBUtils.changeScene(event, "LoginMenu.fxml", "Log in!", null, 0, -1);
			}
		
		});
		
	}
	
	/**
	 * function checks if the given password is in a correct format
	 * @param password password to be evaluated 
	 * @throws IncorrectPasswordFormatException this is user defined exception that returns the errors
	 */
	public static void checkPassword(String password) throws IncorrectPasswordFormatException {
		int nDigits, nCapital, nSmall, nCharacters, nSpecial;
		boolean containsSpace = false;
		
		nDigits = nCapital = nSmall = nCharacters = nSpecial = 0;
		
		for(char c: password.toCharArray()) {
			if(c >= '0' && c <= '9') nDigits++;
			else if(c >= 'a' && c <= 'z') nSmall++;
			else if(c >= 'A' && c <= 'Z') nCapital++;
			else if(c == '@' || c == '!' || c == '#' || c == '$' || c == '&' || c == '%' || c == '.' || c == ',' || c == '-') nSpecial++; 
			else if(c == ' ') {containsSpace = true; break;}
			nCharacters ++;
		}
		
		if(nCharacters < 6) throw new IncorrectPasswordFormatException(1);
		if(nDigits < 1)throw new IncorrectPasswordFormatException(2);
		if(containsSpace)throw new IncorrectPasswordFormatException(3);
		if(nSmall < 1)throw new IncorrectPasswordFormatException(4);
		if(nCapital < 1)throw new IncorrectPasswordFormatException(5);
		if(nSpecial < 1)throw new IncorrectPasswordFormatException(6);
	}
}
