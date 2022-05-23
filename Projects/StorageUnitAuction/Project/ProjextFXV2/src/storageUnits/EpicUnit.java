package storageUnits;

import java.util.ArrayList;

import itemsInUnits.EpicItem;

/**
 * Class creates an epic unit
 * @author Maroš
 */
public class EpicUnit extends RareUnit {
	protected int numOfEpicItems;
	protected ArrayList<EpicItem> epicItemsInUnit;
	
	/**
	 * Constructor creates an Epic unit with existing information
	 * @param unitName existing unit name
	 * @param soldTo existing unit owner
	 * @param unitPrice existing unit price
	 * @param unitValue existing unit value
	 * @param items existing unit epic items
	 */
	public EpicUnit(String unitName, String soldTo, int unitPrice, int unitValue, String items) {
		super(unitName, soldTo, unitPrice, unitValue, items);
	}
	
	/**
	 * creates new random EpicUnit
	 */
	public EpicUnit() {
		super();
		if(this.getClass().getName() == "storageUnits.EpicUnit") {
			setUnitInfo();
			displaySuccess();
		}
	}
	
	/**
	 * returns the number of epic units
	 * @return integer numOfEpicItems
	 */
	public int getNumOfEpicItems() {
		return numOfEpicItems;
	}
	
	/**
	 * sets the info of current unit
	 */
	@Override
	protected void setItemsInUnit() {
		super.setItemsInUnit();
		
		epicItemsInUnit = new ArrayList<>();
		numOfEpicItems = rnd.nextInt(1, 4); // 1 - 3 epic items in unit
		itemsInUnit += String.format("\n\nEpic items in unit: %7d \n", numOfEpicItems) ;
		for(int i=0; i<numOfEpicItems; i++) {
			epicItemsInUnit.add(new EpicItem());
			unitValue += epicItemsInUnit.get(i).getItemValue();
			itemsInUnit += epicItemsInUnit.get(i).getItemName() + ", ";
		}
	}
	
	/**
	 * returns itemsInUnit string
	 * @return String itemsInUnit
	 */
	public String getItemsInUnit() {
		setItemsInUnit();
		return itemsInUnit;
	}
	
	/**
	 * sets the Epic unit information
	 */
	@Override
	protected void setUnitInfo() {
		if(this.getClass().getName() == "storageUnits.EpicUnit") {
			String tmp = getItemsInUnit();
			unitPrice = (int) (unitValue * rnd.nextDouble(0.3, 0.7));
			unitPrice = getUnitPrice();
			unitStartingPrice = getUnitPrice();
			
			unitInfo = String.format("%s unit contains: \nValue: %14d \nPrice: %14d \n"
				+ "Starting price: %5d \n", 
				unitName, getUnitValue(), getUnitPrice(), getUnitStartingPrice());
			unitInfo += tmp;
		}	
	}
}
