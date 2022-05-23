package storageUnits;

import java.util.ArrayList;

import itemsInUnits.RareItem;

/**
 * Class that creates a Rare Unit and gives it its own information
 * It is extended from the common unit
 * @author Maroš
 */
public class RareUnit extends CommonUnit{
	protected int numOfRareItems;
	protected ArrayList<RareItem> rareItemsInUnit;
	
	/**
	 * Constructor used to recreate existing Rare unit
	 * @param unitName existing unit name
	 * @param soldTo existing unit owner
	 * @param unitPrice existing unit price
	 * @param unitValue existing unit value
	 * @param items existing unit items
	 */
	public RareUnit(String unitName, String soldTo, int unitPrice, int unitValue, String items) {
		super(unitName, soldTo, unitPrice, unitValue, items);
	}
	
	/**
	 * constructor to create a new random Rare Unit
	 */
	public RareUnit() {
		super();
		if(this.getClass().getName() == "storageUnits.RareUnit") {
			setUnitInfo();
			displaySuccess();
		}
	}
	
	/**
	 * returns the number of rare items in the unit
	 * @return int numOfRareItems
	 */
	public int getNumOfRareItems() {
		return numOfRareItems;
	}
	
	/**
	 * returns the list of Rare Items inside the unit
	 * @return ArrayList containing rare items
	 */
	public ArrayList<RareItem> getRareItemsInUnit(){
		return rareItemsInUnit;
	}
	
	/**
	 * function that calls Common Unit function and adds info about the rare items to it
	 */
	@Override
	protected void setItemsInUnit() {
		super.setItemsInUnit();
		
		rareItemsInUnit = new ArrayList<>();
		numOfRareItems = rnd.nextInt(1, 11); // 1 - 10 rare items in unit
		itemsInUnit += String.format("\n\nRare items in unit: %d\n", numOfRareItems);
		for(int i=0; i<numOfRareItems; i++) {
			rareItemsInUnit.add(new RareItem());
			unitValue += rareItemsInUnit.get(i).getItemValue();
			itemsInUnit += rareItemsInUnit.get(i).getItemName() + ", ";
		}
	}
	
	/**
	 * returns the string of items inside the unit
	 * @return string of itemsInUnit
	 */
	public String getItemsInUnit() {
		setItemsInUnit();
		return itemsInUnit;
	}
	
	/**
	 * sets the string of unit information 
	 */
	@Override
	protected void setUnitInfo() {
		if(this.getClass().getName() == "storageUnits.RareUnit") {
			String tmp = getItemsInUnit();
			unitPrice = (int) (unitValue * rnd.nextDouble(0.3, 0.7));
			unitPrice = getUnitPrice();
			unitStartingPrice = getUnitPrice();
			
			unitInfo = String.format("%s unit contains: \nValue: %14d \nPrice: %14d \n"
				+ "Starting price: %5d \n", 
				this.getClass().getName(), getUnitValue(), getUnitPrice(), getUnitStartingPrice());
			unitInfo += tmp;
		}	
	}
}
