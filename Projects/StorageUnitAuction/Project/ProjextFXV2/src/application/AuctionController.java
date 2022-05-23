package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

import auctionClasses.Auction;
import auctionClasses.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * class that controls especially the auction flow and its GUI
 * @author Maroš
 */
public class AuctionController implements Initializable
{
	@FXML
	Button bidButton;
	@FXML
	Button statisticsButton;
	@FXML
	Button logoutButton;
	@FXML
	Button addBotButton;
	@FXML
	Button nextAuctionButton;
	@FXML
	TextArea infoArea;
	@FXML
	Text timer;
	@FXML
	Text walletArea;
	@FXML
	ImageView unitImg;
	@FXML
	BorderPane scenePane;
	
	private ArrayList<Image> commonUnitImgs = new ArrayList<>();  
	private ArrayList<Image> rareUnitImgs = new ArrayList<>();  
	private ArrayList<Image> epicUnitImgs = new ArrayList<>();  
	private ArrayList<Image> mysteriUnitImgs = new ArrayList<>();  
	private ArrayList<Image> timerImgs = new ArrayList<>();  
	private Auction auction; // aggregation
	private int bidClicks = 0;
	private Stage stage;
    private	Parent root;
    private Scene scene;
    private User user;
    
    /**
     * sets all needed information to create a new user for the auction
     * @param username users username
     * @param moneyBalance users money balance number
     * @param unitsBought users units bought number
     */
    public void setUserInformation(String username, int moneyBalance, int unitsBought) {
    	start(new User(username, "password", moneyBalance, unitsBought, null));
    }
    
    /**
     * controls if the user pressed any GUI element
     */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		/**
		 * if logout button is pressed, log out the actual user from the auction
		 */
		logoutButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				DBUtils.changeScene(event, "LoginMenu.fxml", "Sign up!", null, 0, 0);
				System.out.println("Users units were sold automatically...");
			}
		});
		
		/**
		 * if the statistics button is pressed it opens a new window containing all needed information about users units
		 */
		statisticsButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("Pressed auctionController.statisticsButton");
				DBUtils.showStatistics(event, user.getName());
			}
		});
	}
    
	/**
	 * refreshes the current GUI window
	 * @param event
	 */
	public void refresh(ActionEvent event) {
		event.getSource().notifyAll();
	}
	
	/**
	 * connects all needed images to the current auction such as units images and timer images
	 */
	private void connectImages() {
		String projectDir = System.getProperty("user.dir");		
		projectDir = projectDir.replace("\\", "\\\\");
		projectDir += "\\\\Images\\\\";
		
		// System.out.println(projectDir);

		commonUnitImgs.add(new Image(projectDir + "CommonUnit1.jpg"));
		commonUnitImgs.add(new Image(projectDir + "CommonUnit2.jpg"));
		commonUnitImgs.add(new Image(projectDir + "CommonUnit3.jpg"));
		
		rareUnitImgs.add(new Image(projectDir + "RareUnit.jpg"));
		rareUnitImgs.add(new Image(projectDir + "RareUnit1.jpg"));
		rareUnitImgs.add(new Image(projectDir + "RareUnit2.jpg"));
		rareUnitImgs.add(new Image(projectDir + "RareUnit3.jpg"));
		
		epicUnitImgs.add(new Image(projectDir + "EpicUnit.jpg"));
		epicUnitImgs.add(new Image(projectDir + "EpicUnit1.jpg"));
		epicUnitImgs.add(new Image(projectDir + "EpicUnit2.jpg"));
		epicUnitImgs.add(new Image(projectDir + "EpicUnit3.jpg"));

		mysteriUnitImgs.add(new Image(projectDir + "MysteriousUnit0.jpg"));
		mysteriUnitImgs.add(new Image(projectDir + "MysteriousUnit1.jpg"));		
	
		timerImgs.add(new Image(projectDir + "zero.jpg"));
		timerImgs.add(new Image(projectDir + "one.jpg"));
		timerImgs.add(new Image(projectDir + "two.jpg"));
		timerImgs.add(new Image(projectDir + "three.jpg"));
	}
	
	/**
	 * joins a given text to the GUI text field 
	 * @param text text to be appended
	 */
	public void appendToOutput(String text) {	
		String newText = infoArea.getText() + text + "\n";
		infoArea.setText(newText);
		infoArea.setScrollLeft(0);
		infoArea.setScrollTop(1000000);
	}
	
	/**
	 * function that sets the timer to a given value
	 * @param time time to be set
	 */
	public void setTimer(int time) {
		timer.setText(String.valueOf(time));
	}
	/**
	 * If bid button is pressed, this function manages all needed information about the current user and unit. 
	 * It sets the actual unit, user...
	 */
	public void bidButton() {
		if(bidClicks++ == 0) {
			auction.startAuction();
		}
		else if (auction.getUser().getBalance() >= auction.getUnit().getUnitPrice()){
			auction.setLastBid(auction.getUser());
			auction.resetTimer();	
			appendToOutput(String.format("%s bid %d$", auction.getUser().getName(), auction.getUnit().getUnitPrice()));
			auction.getUnit().increasePrice();		
			
			DBUtils.updateActualUnit(auction.getUnit().getClass().getName(), auction.getUnit().getUnitPrice(), auction.getUser().getName());
		}
		else {
			appendToOutput(String.format("%s you do not have enough money!", auction.getUser().getName()));
		}
		
		bidButton.setText(String.format("Bid %d$", auction.getUnit().getUnitPrice()));
		updateWallet(user);
	}
	
	/**
	 * updates wallet text field
	 * @param user actual user and his attributes
	 */
	public void updateWallet(User user) {
		walletArea.setText(String.format("Name: %s\nBalance: %d$ \nUnits bought: %d", user.getName(), user.getBalance(), user.getUnitsBought()));
	}
	
	/**
	 * function where auction starts, auction is created, user is set and GUI reseted
	 * @param user actual user
	 */
	public void start(User user){
		auction = new Auction(this, user);
		this.user = user;
		user.setAuction(auction);
		auction.notifyAllObservers();
		DBUtils.actualUser = user;
		
		connectImages();
		
		// show random image (depends also on its quality)
		if(auction.getUnit().getClass().getName() == "storageUnits.CommonUnit")
			unitImg.setImage(commonUnitImgs.get(new Random().nextInt(0, commonUnitImgs.size())));
		else if(auction.getUnit().getClass().getName() == "storageUnits.RareUnit")
			unitImg.setImage(rareUnitImgs.get(new Random().nextInt(0, rareUnitImgs.size())));
		else if(auction.getUnit().getClass().getName() == "storageUnits.EpicUnit")
			unitImg.setImage(epicUnitImgs.get(new Random().nextInt(0, epicUnitImgs.size())));
		else if(auction.getUnit().getClass().getName() == "storageUnits.MysteriousUnit")
			unitImg.setImage(mysteriUnitImgs.get(new Random().nextInt(0, mysteriUnitImgs.size())));
		unitImg.autosize();
		bidButton.setText("Start auction");
		updateWallet(this.user);
		infoArea.setText("Auction Info\n\n");
		timer.setText("9");
	}
	
	/**
	 * if next auction button is pressed it opens a new window and new auction
	 * @param event
	 * @throws IOException
	 */
	public void nextAuction(ActionEvent event) throws IOException {
		System.out.println("Next auction");
		stage = (Stage) scenePane.getScene().getWindow();
		stage.close();
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Auction.fxml"));
		root = loader.load();
		
		AuctionController auctionController = loader.getController();
		auctionController.start(auction.getUser());
		
		stage = (Stage)((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}
