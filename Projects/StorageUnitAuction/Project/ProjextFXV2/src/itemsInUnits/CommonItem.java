package itemsInUnits;

import java.util.Random;

/**
 * Class common item that creates the current item and also makes a base for other classes
 * @author Maroš
 *
 */
public class CommonItem {
	protected int itemValue; // from 1$ - 10$
    protected String itemName;
    protected Random rnd;

    private String[] commonItemNames = {"Bike", "Iron", "Shoes", 
			"Doll", "Chair", "Dry rolls", "Bucket", "Iron hoe", "Bottles",
			"Bone", "Mask", "Cards", "Tin cans", "Calculator"};
    // my sql database
	
    /**
     * constructor that gives a name to the item and also a value
     */
	public CommonItem() {
		rnd = new Random();
		itemValue = rnd.nextInt(1, 11);
		itemName = commonItemNames[rnd.nextInt(commonItemNames.length)];
	}
	
	/**
	 * returns items name
	 * @return String itemName
	 */
	public String getItemName() {
		return itemName;
	}
	
	/**
	 * returns Items value
	 * @return int itemValue
	 */
	public int getItemValue() {
		return itemValue;
	}
}
