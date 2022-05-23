package storageUnits;

import java.util.ArrayList;

import itemsInUnits.MysteriousItem;

/**
 * Creates a mysterious unit containing special items inside
 * @author Maroš
 *
 */
public class MysteriousUnit extends EpicUnit{
	private int numOfMysteriousItems;
	private ArrayList<MysteriousItem> mysteriousItemsInUnit;
	
	/**
	 * creates existing mysterious unit with given parameters
	 * @param unitName existing unit name
	 * @param soldTo existing unit owner
	 * @param unitPrice existing unit price
	 * @param unitValue existing unit value
	 * @param items existing unit items
	 */
	public MysteriousUnit(String unitName, String soldTo, int unitPrice, int unitValue, String items) {
		super(unitName, soldTo, unitPrice, unitValue, items);
	}
	
	/**
	 * creates a random mysterious unit 
	 */
	public MysteriousUnit() {
		super();
		setUnitInfo();
		displaySuccess();
	}
	
	/**
	 * returns number of mysterious units inside the unit
	 * @return int numOfMysteriousItems
	 */
	public int getNumOfMysteriousItems() {
		return numOfMysteriousItems;
	}
	
	/**
	 * sets items information of the unit
	 */
	@Override
	protected void setItemsInUnit() {
		super.setItemsInUnit();
		
		mysteriousItemsInUnit = new ArrayList<>();
		numOfMysteriousItems = rnd.nextInt(1, 3); // 1 - 2 mysterious items in unit
		itemsInUnit += String.format("\n\nMysterious items in unit: %d\n", numOfMysteriousItems);
		for(int i=0; i<numOfMysteriousItems; i++) {
			mysteriousItemsInUnit.add(new MysteriousItem());
			unitValue += mysteriousItemsInUnit.get(i).getItemValue();
			itemsInUnit += mysteriousItemsInUnit.get(i).getItemName() + ", ";
		}
		unitPrice = unitValue;
		for(int i=0; i<numOfMysteriousItems; i++) {
			unitPrice -= mysteriousItemsInUnit.get(i).getItemValue();
		}
	}
	
	/**
	 * returns items inside the unit
	 * @return String itemsInUnit
	 */
	public String getItemsInUnit() {
		setItemsInUnit();
		return itemsInUnit;
	}
	
	/**
	 * sets all unit information
	 */
	@Override
	protected void setUnitInfo() {
		if(this.getClass().getName() == "storageUnits.MysteriousUnit") {
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
