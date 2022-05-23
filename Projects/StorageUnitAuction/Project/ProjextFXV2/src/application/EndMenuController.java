package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import storageUnits.CommonUnit;

/**
 * Class that controls end menu statistics
 * @author Maroš
 *
 */
public class EndMenuController implements Initializable {
	@FXML
	public Button newAuctionButton;
	@FXML
	public Button logOutButton;
	@FXML
	public Button sellMyUnitsButton;
	@FXML
	public Button showMyUnits;
	@FXML
	public Button showAllUnits;
	
	@FXML
	public TableView<CommonUnit> table;
	
	/** 
	 * function updates the rows in the table
	 * @param name actual user name
	 */
	public void updateTable(String name) {
		for (CommonUnit commonUnit : DBUtils.getUnits(name)) {
			table.getItems().addAll(commonUnit);
		}
	}
	
	/**
	 * checks if user pressed any GUI element and runs the special function if yes
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		newAuctionButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("Clicked EndMenu.newAuctionButton");
				DBUtils.changeScene(event, "Auction.fxml", "Storage Unit Auction", DBUtils.actualUser.getName(), DBUtils.actualUser.getBalance(), DBUtils.actualUser.getUnitsBought());
			}
		});
		
		logOutButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("Clicked EndMenu.LogOutButton");
				DBUtils.changeScene(event, "LoginMenu.fxml", "Log in!", null, 0, 0);
				System.out.println("Users units were sold automatically...");
			}
		});
		
		sellMyUnitsButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("Clicked EndMenu.SellMyUnitsButton");			
				
				table.getItems().clear();
				updateTable("sell");
				updateTable(null);
			}
		});
		
		showMyUnits.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("Clicked EndMenu.showMyUnitsButton");			
				
				table.getItems().clear();
				updateTable(DBUtils.actualUser.getName());
			}
		});
		
		showAllUnits.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("Clicked EndMenu.showAllUnitsButton");			
				
				table.getItems().clear();
				updateTable(null);
			}
		});
		
		TableColumn<CommonUnit, String> unitName = new TableColumn<>("Unit name");
		TableColumn<CommonUnit, String> unitPrice = new TableColumn<>("Price");
		TableColumn<CommonUnit, String> unitBuyer = new TableColumn<>("Sold to");
		
		unitName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUnitName()));
		unitBuyer.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOwner()));
		unitPrice.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUnitPriceString()));
		
		table.getColumns().addAll(unitName);
		table.getColumns().addAll(unitPrice);
		table.getColumns().addAll(unitBuyer);
		
		updateTable(null);
	}
}