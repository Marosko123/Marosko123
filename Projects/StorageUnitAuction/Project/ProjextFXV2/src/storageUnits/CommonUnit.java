package storageUnits;

import java.util.ArrayList;
import java.util.Random;


import auctionClasses.AuctionFollower;
import itemsInUnits.CommonItem;

/**
 * Common unit is the class creating the base of other units.
 * It stores some base values such as price, name, owner...
 * @author Maroš
 */
public class CommonUnit implements AuctionFollower, StorageUnitType {
	protected int unitPrice = 50;
	protected int unitValue = 50;
	protected int unitStartingPrice;
	protected int numOfCommonItems;
	protected int defaultIncrease = 50;
	protected Random rnd;	
	protected String unitName;
	protected String itemsInUnit = "";
	protected String owner = "";
	
	protected ArrayList<CommonItem> commonItemsInUnit;
	protected String unitInfo = "";
	
	/**
	 * Constructor which creates a new common unit with given parameters
	 * @param unitName name of the current unit
	 * @param soldTo owner of the current unit
	 * @param unitPrice price of the current unit
	 * @param unitValue value of the current unit
	 * @param items items inside the current unit
	 */
	public CommonUnit(String unitName, String soldTo, int unitPrice, int unitValue, String items) {
		this.unitName = unitName;
		this.owner = soldTo;
		this.unitPrice = unitPrice;
		this.unitValue = unitValue;
		this.itemsInUnit = items;
	}
	
	/**
	 * constructor which creates a random items without given parameters
	 */
	public CommonUnit(){
		
		commonItemsInUnit = new ArrayList<>();
		rnd = new Random();
		numOfCommonItems = rnd.nextInt(1, 26); // 1 - 25 common items in unit
		for(int i = 0; i < numOfCommonItems; i++) {
			commonItemsInUnit.add(new CommonItem());
			unitValue += commonItemsInUnit.get(i).getItemValue();
		}
		unitPrice = (int) (unitValue * rnd.nextDouble(0.3, 0.7));
		unitPrice = getUnitPrice();
		unitStartingPrice = getUnitPrice();
		
		// RTTI
		if(this.getClass().getName() == "storageUnits.CommonUnit") {
			setUnitInfo();
			displaySuccess();
		}
	}
	
	/**
	 * sets the name of the current unit
	 * @param unitName name to be set
	 */
	public void setUnitName(String unitName) {
    	this.unitName = unitName;
    }
	
	/**
	 * sets the name of the actual owner
	 * @param name to be set
	 */
	public void setOwner(String name) {
		owner = name;
	}
	
	/**
	 * function that returns unit name
	 * @return unit name
	 */
	public String getUnitName() {
		return unitName;
	}
	
	/**
	 * returns units owner name
	 * @return owner Name
	 */
	public String getOwner() {
		return owner;
	}
	
	/**
	 * increases the unit price by a default value
	 */
	public void increasePrice() {
		unitPrice += defaultIncrease;
	}	

	/**
	 * returns the default increase
	 * @return integer default increase 
	 */
	public int getDefaultIncrease() {
		return defaultIncrease;
	}
	
	/**
	 * returns the unit price in string
	 * @return string of the actual price
	 */
	public String getUnitPriceString() {
		return String.valueOf(unitPrice / 50 * 50);
	}
	
	/**
	 * returns actual unit price
	 * @return integer actual unit price
	 */
	public int getUnitPrice() {
		return unitPrice / 50 * 50;
	}
	
	/**
	 * returns the unit value
	 * @return integer unit value
	 */
	public int getUnitValue() {
		return unitValue;
	}
	
	/**
	 * function sets the unit value
	 * @param unitValue value to be set
	 */
	public void setUnitValue(int unitValue) {
		this.unitValue = unitValue;
	}
	
	/**
	 * function returns the starting price of the unit
	 * @return unit starting price
	 */
	public int getUnitStartingPrice() {
		return this.unitStartingPrice;
	}
	
	/**
	 * function returns commonItems inside the unit
	 * @return common items in unit
	 */
	public ArrayList<CommonItem> getCommonItemsInUnit(){
		return commonItemsInUnit;
	}
	
	/**
	 * function returns the actual information about the unit
	 * @return string unit info
	 */
	public String getUnitInfo() {
		return unitInfo;
	}
	
	/**
	 * returns the number of common items inside the unit
	 * @return int numOfCommonItems
	 */
	protected int getNumOfCommonItems() {
		return numOfCommonItems;
	}
	
	/**
	 * sets the string of items in unit
	 */
	protected void setItemsInUnit() {
		itemsInUnit += String.format("\nCommon items in unit: %d\n", numOfCommonItems);
		
		commonItemsInUnit.forEach((n) -> {itemsInUnit += n.getItemName() + ", ";}); // lambda operator to add items in unit
	}
	
	/**
	 * function returns the string of items inside the unit
	 * @return string items in unit
	 */
	public String getItemsInUnit() {
		setItemsInUnit();
		return itemsInUnit;
	}
	
	/**
	 * function that sets a string about all unit information such as, name, price, value
	 */
	protected void setUnitInfo() {
		if(this.getClass().getName() == "storageUnits.CommonUnit") {
			unitInfo = String.format("%s unit contains: \nValue: %14d \n"
				+ "Starting price: %5d \n", 
				this.getClass().getName(), getUnitValue(), getUnitStartingPrice());
			unitInfo += getItemsInUnit();
		}			
	}
	
	/**
	 * informs about the state inside the unit
	 */
	public void inform() {
		System.out.println("Something is happening in " + unitName);
	}

	/**
	 * part of the FACTORY PATTERN
	 * @return returns classType name
	 */
	@Override
	public String returnUnitType() {
		System.out.println("FACTORY PATTERN info: " + this.getClass().getName());
		return this.getClass().getName();
	}
}
