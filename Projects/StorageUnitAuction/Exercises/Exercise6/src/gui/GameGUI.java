package gui;

import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class GameGUI extends Application {
	private Button nextClashWindowButton = new Button("Ogres and Knights");
		
	public void start(Stage mainWindow) {
		mainWindow.setTitle("Clash");
		
		FlowPane pane = new FlowPane();
		
		pane.getChildren().add(nextClashWindowButton);
		
		nextClashWindowButton.setOnAction(e -> new ClashWindow());

		mainWindow.setScene(new Scene(pane, 400, 250));
		mainWindow.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
