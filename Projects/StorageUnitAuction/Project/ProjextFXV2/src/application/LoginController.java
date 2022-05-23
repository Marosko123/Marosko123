package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *  Class that controls the login menu of the FX application
 */
public class LoginController implements Initializable{	
	@FXML
	TextField usernameTextField;
	@FXML
	PasswordField passwordField;
	@FXML
	Button loginButton;
	@FXML
	Button registerButton;
	@FXML
	Button checkConnectionButton;
	@FXML
    Text wrongInputText;
	@FXML
	Label connectionLabel;
	@FXML
	ImageView connectImg;
	
	Parent root;
	Stage stage;
	Scene scene;

	private ArrayList<Image> DBconImgs = new ArrayList<>(); 
	
	/**
	 *  gives the information about clicked buttons in the login menu
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		/**
		 * if register button is pressed, open the registration menu
		 */
		registerButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("Register button pressed");
				DBUtils.changeScene(event, "RegisterMenu.fxml", "Sign up!", null, 0, -1);
			}
		});
		
		/**
		 * if login button is pressed, try if the information is correct
		 * if yes, open the auction, else stays in login menu 
		 */
		loginButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("Login button pressed");
				DBUtils.logInUser(event, usernameTextField.getText(), passwordField.getText());
			}
		});
	}
	
	/**
	 *  checks whether is database connected or not and displays proper image
	 */
	public void checkConnection(ActionEvent actionEvent) {
		String projectDir = System.getProperty("user.dir");		
		projectDir = projectDir.replace("\\", "\\\\");
		projectDir += "\\\\Images\\\\";
		DBconImgs.add(new Image(projectDir + "DBnotConnected.png"));
		DBconImgs.add(new Image(projectDir + "DBConnected.png"));
		
		connectImg.setImage(DBconImgs.get(0));
		
		if(DBUtils.getConnection() != null) {
			connectionLabel.setText("Connected :)");
			connectImg.setImage(DBconImgs.get(1));
		}
		else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Problem with connecting to the database... \nUnable to run the program");
			alert.show();
		}
	}
}
