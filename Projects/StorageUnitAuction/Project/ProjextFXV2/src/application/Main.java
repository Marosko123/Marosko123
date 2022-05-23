package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * Application starts inside this Main class
 * Run main to start
 */
public class Main extends Application {	
	/**
	 * start function overrides Application function to use FX graphics
	 * on start, resets unit information databases and starts the FX program
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		DBUtils.resetTable("sold_units");
		DBUtils.resetTable("actual_unit");
		Parent root = FXMLLoader.load(getClass().getResource("LoginMenu.fxml"));
		primaryStage.setTitle("Log in!");
		primaryStage.setScene(new Scene(root));
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	/**
	 * main function calls FX graphics function to start the GUI
	 */
	public static void main(String[] args) {		
		launch(args);
	}
}

// --module-path D:\JavaFX\javafx-sdk-18\lib --add-modules javafx.controls,javafx.fxml
