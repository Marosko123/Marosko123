package auctionClasses;

import java.io.Serializable;

import application.AuctionObserver;
import application.DBUtils;
import storageUnits.CommonUnit;

/**
 * Class containing all the needed information about the user 
 * Such as name, passwod, balance, units bought and actual unit
 */
public class User extends AuctionObserver implements Serializable{
	private static final long serialVersionUID = 1L;
	protected String name;
	protected String password;
	protected int balance;
	protected int unitsBought = 0;
	protected CommonUnit actualUnit = null;
	
	/**
	 * Constructor to create an User object 
	 */
	public User(String name, String password, int balance, int unitsBought, Auction auction) {
		this.name = name;
		this.balance = balance;
		this.password = password;
		this.unitsBought = unitsBought;
		
	}
	
	/**
	 * @return which returns a number of units which user bought
	 */
	public int getUnitsBought() {
		return unitsBought;
	}
	
	/**
	 * Function to increase number of bought units + 1
	 */
	public void increaseUnitsBought() {
		unitsBought++;
	}
	
	/**
	 * @return the users name
	 */
	public String getName(){
		return name;
	}

	public void setAuction(Auction auction) {
		this.auction = auction;
		this.auction.attach(this);
	}
	/**
	 * @return the current money balance of the user
	 */
	public int getBalance() {
		return balance;
	}
	
	/**
	 * Part of the STRATEGY PATTERN
	 * adds the money if the amount is non negative
	 * if is negative, it subtracts the amount
	 * @param ammount increments/decrements the money balance of the user
	 */
	public void addMoney(int amount) {
		Context context = null;
		
		if(amount >= 0) context = new Context(new OperationAddMoney());
		else context = new Context(new OperationSubMoney());
		balance = context.executeStrategy(balance, amount);
		DBUtils.updateUserInformation(name, amount, unitsBought);
	}
	
	/**
	 *  function which overrides the Observer and updates the actual unit...
	 *  This is the OBSERVER PATTERN
	 */
	@Override
	public void update() {
		actualUnit = auction.getUnit();
		System.out.println("OBSERVER PATTERN: NEW auction unit is " + auction.getUnit().getClass().getName());
	}

	/**
	 * function which prints the user data such as name, password, balance and number of bought units 
	 */
	public void printUserData() {
		System.out.println("  Name: " + name);
		System.out.println("  Password: " + password);
		System.out.println("  Money balance: " + balance);
		System.out.println("  Units bought: " + unitsBought);
	}
}
