package itemsInUnits;

/**
 * Class Epic item that is extended from Rare item class
 * it contains different values
 * @author Maroš
 *
 */
public class EpicItem extends CommonItem {
	private String[] epicItemNames = {"Car", "Motocycle", "Supreme shirt",
			"1 Ethereum", "1 000 000 Shiba tokens", "Gold nugget", 
			"Jedays lighting sword", "Thanos glove", "Jetpack", 
			"Dead heaven star", "The first Pokemon card", "Gombikovas wheelie"};
	
	/**
	 * Constructor that creates random Epic item
	 */
	public EpicItem() {
		itemValue = rnd.nextInt(400, 3001); // 400$ - 3000$
		itemName = epicItemNames[rnd.nextInt(epicItemNames.length)];
	}
}
