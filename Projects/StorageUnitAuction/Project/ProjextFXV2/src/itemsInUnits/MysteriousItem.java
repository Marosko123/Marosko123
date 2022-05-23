package itemsInUnits;

/**
 * Class Mysterious item that is extended from Epic item class
 * it contains different values
 * @author Maroš
 *
 */
public class MysteriousItem extends CommonItem {
	private String[] mysteriousItemNames = {"Signature", "Book",
			"Painting", "Jewelery", "Old gun", "Airplane model"};
	private boolean scam;
	
	/**
	 * constructor that creates new random Mysterious unit
	 */
	public MysteriousItem() {
		itemName = mysteriousItemNames[rnd.nextInt(mysteriousItemNames.length)];
		scam = rnd.nextBoolean();
		if(scam) {
			System.out.printf("Unfortunately, item %s is fake...\n", itemName);
			itemValue = rnd.nextInt(50, 101); // 50$ - 100$
		}
		else {
			System.out.println("Wohou, do you even know what did you find?");
			System.out.printf("It is original %s and there is no more of them!", itemName);
			itemValue = rnd.nextInt(1000, 10001); // 1 000$ - 10 000$}
		}
	}
}
