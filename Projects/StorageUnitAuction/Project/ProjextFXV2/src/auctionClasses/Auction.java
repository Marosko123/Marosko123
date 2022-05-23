package auctionClasses;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import application.AuctionController;
import application.DBUtils;
import application.AuctionObserver;
import storageUnits.CommonUnit;

/**
 * Class which manages the backend of the application
 * Implements serializable, so we can store all the information about the current auction and send it to others via internet
 */
public class Auction implements AuctionFollower, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CommonUnit actualUnit; // aggregation
	private UnitFactory unitFactory = new UnitFactory();
	private Timer timer = new Timer();  
	private int timeLeft = 9;
	private AuctionController auctionController;  // aggregation
	private User lastBidPlayer = null;
	private boolean isSold;
	private ArrayList<CommonUnit> soldUnits = new ArrayList<>();
	private User user; // aggregation
	private List<AuctionObserver> observers = new ArrayList<AuctionObserver>();

	/**
	 * connects the observer to the list of observers
	 * @param observer observer is someone who wants to be informed about the auction
	 */
	public void attach(AuctionObserver observer){
	    observers.add(observer);		
	}
	
	/**
	 * OBSERVER PATTERN
	 * function which notifies all the observers and updates current information
	 */
	public void notifyAllObservers(){
	    for (AuctionObserver observer : observers) {
	         observer.update();
	    }
	} 	
	
	/**
	 * @return user gets returned here
	 */
	public User getUser() {
		return user;
	}
	
	/**
	 * function sets the last bidder of the current auction
	 * @param lastBid lastBid user is the User which bid as the last 
	 */
	public void setLastBid(User lastBid) {
		this.lastBidPlayer = lastBid;
	}
	
	/**
	 * Constructor creating the current auction with given information
	 * Also, it calls a function to create a new unit which will be sold in the auction
	 * @param auctionController auctionController is object where graphics are changed
	 * @param user user is the current logged in user
	 */
	public Auction(AuctionController auctionController, User user) {
		this.auctionController = auctionController;
		this.user = user;
		isSold = false;
		
		createNewUnit();
	}

	/**
	 * function creates actual unit which will be sold
	 * also it is attaching the sold unit to the database
	 */
	public void createNewUnit() {
		this.actualUnit = unitFactory.generateNewUnit();
		DBUtils.updateActualUnit(actualUnit.getClass().getName(), actualUnit.getUnitPrice(), null);
		soldUnits.add(actualUnit);
	}
	
	/**
	 * @return soldUnits returns all the sold units that were in the auction
	 */
	public ArrayList<CommonUnit> getAllUnits(){
		return soldUnits;
	}
	
	/**
	 * @return actualUnit returns the actual unit which is being sold
	 */
	public CommonUnit getUnit() {
		return actualUnit;
	}
	
	/**
	 * function sets the timer to its origin value
	 */
	public void resetTimer() {
		timeLeft = 9;
	}
	
	/**
	 * @return timeLeft returns the remaining time in the timer
	 */
	public int getTimeLeft() {
		return timeLeft;
	}
	
	/**
	 * returns the boolean true if the actual unit is sold, false if is not sold
	 */
	public boolean getSold() {
		return isSold;
	}
	
	/**
	 * starts the current auction
	 * timer is turned on and counts down the time to finish the auction
	 */
	public void startAuction() {
		timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
            	timeLeft--;
       
            	auctionController.setTimer(timeLeft+1);
            	Random rnd = new Random();
            	
            	// random bids to the auction
            	Bot bot = new Bot("Bot"+ String.valueOf(rnd.nextInt()), "", rnd.nextInt(1000, 5000), 0, null);         	

            	int greedLevel = 0;
            	
            	
            	// lowest greed 10
            	// highest greed 50
            	if(actualUnit.getClass().getName() == "storageUnits.CommonUnit")
            		greedLevel = (10 - timeLeft) * 2;
            	else if(actualUnit.getClass().getName() == "storageUnits.RareUnit")
            		greedLevel = (int)((10 - timeLeft) * 2.5);
            	else if(actualUnit.getClass().getName() == "storageUnits.EpicUnit")
            		greedLevel = (int)((10 - timeLeft) * 3);
            	else 
            		greedLevel = (10 - timeLeft) * 5;
            	
            	// if bot wants to bid, it bids the actual amount of money 
            	if(bot.wantsToBit(greedLevel, actualUnit.getUnitPrice())) {
            		setLastBid(bot);
        			resetTimer();	
        			auctionController.appendToOutput(String.format("%s bid %d$", bot.getName(), getUnit().getUnitPrice()));
        			actualUnit.increasePrice();	
            	}
            	
                if (timeLeft < 0) {
                    timer.cancel();
                    System.out.println("TIME IS OVER");
                    if(lastBidPlayer != null) {
                    	calculate();                    	
                    }
                    else {
                    	auctionController.appendToOutput(String.format("\nUnit was not sold ...\n\n"));
                    }
                    isSold = true;    
                    DBUtils.soldUnit(actualUnit.getClass().getName(), actualUnit.getUnitPrice(), actualUnit.getUnitValue(), lastBidPlayer.getName(), actualUnit.getItemsInUnit());
                    DBUtils.updateUserInformation(lastBidPlayer.getName(), lastBidPlayer.getBalance(), lastBidPlayer.getUnitsBought());
                }
            }
        }, 0, 1000);
	}
	
	/**
	 *  calculates the current information and informs all users about thhat
	 */
	private void calculate() {
		System.out.println("Calculate");
		lastBidPlayer.addMoney(-(actualUnit.getUnitPrice() + 50));
		lastBidPlayer.increaseUnitsBought();
		auctionController.appendToOutput(String.format("Unit sold for %d$\n\n", actualUnit.getUnitPrice()-50));
		auctionController.appendToOutput(actualUnit.getUnitInfo());
		notifyAllObservers();
	}

	/**
	 *  Displays the current unit information
	 */
	@Override
	public void inform() {
		System.out.println("This is the unit information: ");
        System.out.printf("  Unit name: %s", actualUnit.getUnitName());
	}
}
