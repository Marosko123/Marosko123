package gui;

import game.*;
import characters.*;
import javafx.application.*;
import javafx.concurrent.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.layout.*;
import javafx.stage.*;
//import javafx.event.*;
import java.io.*;
import java.util.*;

public class ClashWindow extends Stage {
	private Button createWarriorsButton = new Button("Create Warriors");
	private Button initClashButton = new Button("Initiialize Clash");
	private Button nextClashButton = new Button("Next Clash");
	private Button clashButton = new Button("Full Clash");
	private Button saveButton = new Button("Save");
	private Button loadButton = new Button("Load");
	private Button countButton = new Button("Count");
	private TextField knights = new TextField();
	private TextField braveKnights = new TextField();
	private TextField badOgres = new TextField();
	private TextField warriorType = new TextField();
	private Label knightsLabel = new Label("Knights");
	private Label braveKnightsLabel = new Label("Brave knights");
	private Label badOgresLabel = new Label("Bad ogres");
	private Label warriorTypeLabel = new Label("Warrior type");
	private TextArea output = new TextArea();
	private ScrollPane scroll = new ScrollPane();

	private EnergyOfWarriors energyOfWarriors;
	
	public ClashWindow() {
		super();
		
		setTitle("Clash");
		
		FlowPane pane = new FlowPane();
		
		Game game = new Game();
		
		pane.getChildren().add(createWarriorsButton);
		pane.getChildren().add(initClashButton);
		pane.getChildren().add(nextClashButton);
		pane.getChildren().add(clashButton);
		pane.getChildren().add(saveButton);
		pane.getChildren().add(loadButton);
		pane.getChildren().add(countButton);
		pane.getChildren().add(knights);
		pane.getChildren().add(knightsLabel);
		pane.getChildren().add(braveKnights);
		pane.getChildren().add(braveKnightsLabel);
		pane.getChildren().add(badOgres);
		pane.getChildren().add(badOgresLabel);
		pane.getChildren().add(output);
		pane.getChildren().add(warriorType);
		pane.getChildren().add(warriorTypeLabel);
		
		scroll.setContent(pane);		
		
		createWarriorsButton.setOnAction(e -> {
			try {
				if(knights.getText() != "" && braveKnights.getText() != ""
						&& badOgres.getText() != "") {
			
		
				game.createWarriors(Integer.parseInt(knights.getText()),
						Integer.parseInt(braveKnights.getText()),
						Integer.parseInt(badOgres.getText()),
						new AlternateSwordsSquadGameSetup());
				output.clear();
				output.appendText("Warriors created.\n");
				}
			} catch (UnevenNumberOfWarriorsException ex) {
				Alert a = new Alert(AlertType.ERROR);
				a.setTitle("Error");
				a.setContentText("The count of bad ogres cannot be greater than the overall count of knights.");
				a.showAndWait();
			}
		});

		initClashButton.setOnAction(e -> {
			output.appendText("\n" + "Ready for the next clash.\n\n");
			game.initClash();
		});
		
		nextClashButton.setOnAction(e -> {
 			try {
 				output.appendText(game.nextClash1on1());
 			} catch (NoSuchElementException ex) { // NoSuchElementException is a so-called uncontrolled exception, but such exceptions are also being propagated
				Alert a = new Alert(AlertType.ERROR);
				a.setTitle("Error");
				a.setContentText("There is no next pair of warriors.");
				a.showAndWait(); 					 				
 			}
 		});

 		clashButton.setOnAction(e -> {
// 			output.appendText(game.clash()); // bad because we are clogging the GUI with a complex calculation
 			
 			// Two ways of executing a complex calculation outside the thread in which GUI (JavaFX) is being executed
 			// 1. By putting the calculation directly in a separate thread
/*
 			Thread th = new Thread() {
				public void run() {
					String s = game.clash();
					Platform.runLater(() -> output.appendText(s)); // we must change the GUI exclusively via the JavaFX application thread
				}
		    };
*/
		    // 2. By wrapping the calcualtion in an object of the Task<V> class and supplying this to a separate thread

		    Task<String> t = new Task<String>() {
 				protected String call() {
 					return game.clash();
 				}
 				protected void succeeded() {
 					Platform.runLater(() -> output.appendText(getValue()));
 				}
 			};
 
 			// 2a. Capturing an exception by observing the task failure
/*
 			t.setOnFailed(ee -> {
 				Exception ex = (Exception) t.getException();
 				if (t.getException() instanceof NullPointerException) {
 					Alert a = new Alert(AlertType.ERROR);
 					a.setTitle("Error");
 					a.setContentText("The warriors have not been initialized.");
 					a.showAndWait();
 					// ex.printStackTrace(); // if we want to print the name of the exception and the call stack
 				}
 			});
*/

			// 2b. Capturing an exception by observing the object which is used to store exceptions
 			t.exceptionProperty().addListener((observable, oldValue, newValue) ->  {
 				if(newValue != null) {
 					Exception ex = (Exception) newValue;
 	 				if (ex instanceof NullPointerException) {
 	 					Alert a = new Alert(AlertType.ERROR);
 	 					a.setTitle("Error");
 	 					a.setContentText("The warriors have not been initialized.");
 	 					a.showAndWait();
 	 					// ex.printStackTrace(); // if we want to print the name of the exception and the call stack
 	 				}
 				}
 			});
			
 			Thread th = new Thread(t);

			// in both variants, we set the thread to be a daemon, i.e. not to block program (virtual machine) termination
 			th.setDaemon(true); // the thread won't block exiting JVM
 			th.start();
 		});
 		
  		saveButton.setOnAction(e -> {
			try {
				game.save();
			} catch (ClassNotFoundException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
 		
 		loadButton.setOnAction(e -> {
			try {
				game.load();
			} catch (ClassNotFoundException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

 		countButton.setOnAction(e -> { 			
 			switch (warriorType.getText()) {
 				case ("k") : output.appendText("Knights: " + game.countWarriors(Knight.class) + "\n");
 				break;
 				case ("bk") : output.appendText("Brave knights: " + game.countWarriors(BraveKnight.class) + "\n");
 				break;
 				case ("o") : output.appendText("Ogres: " + game.countWarriors(Ogre.class) + "\n");
 				break;
 				case ("bo") : output.appendText("Bad ogres: " + game.countWarriors(BadOgre.class) + "\n");
 				break; 			
 			}
		});
	
 		energyOfWarriors = new EnergyOfWarriors(game);
		game.followClash(energyOfWarriors);
		pane.getChildren().add(energyOfWarriors);

		setScene(new Scene(scroll, 500, 300));
		show();
	}
}
