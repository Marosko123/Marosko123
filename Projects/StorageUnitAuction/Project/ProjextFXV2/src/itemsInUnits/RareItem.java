package itemsInUnits;

/**
 * Class Rare item that is extended from Common item class
 * it contains different values
 * @author Maroš
 *
 */
public class RareItem extends CommonItem {
	private String[] rareItemNames = {"Washingmashine", "Dishwasher",
			"Phone", "Laptop", "Weights", "Tires", "Snowboard", "MP4-player",
			"Xbox360", "PlayStation4", "Windows Licence"};
	
	/**
	 * Constructor that creates new random rare item 
	 */
	public RareItem() {
		itemValue = rnd.nextInt(11, 101); // 11$ - 100$
		itemName = rareItemNames[rnd.nextInt(rareItemNames.length)];
	}
}
