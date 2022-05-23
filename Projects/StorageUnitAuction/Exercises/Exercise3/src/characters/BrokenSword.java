package characters;

public class BrokenSword extends Sword {

	public BrokenSword(int productNumber) {
		super(productNumber);
	}

	@Override
	public void hit(Knight knight, Ogre ogre) {
		System.out.println("Knight - " +"This sword is too broken to kill anyone");
	}

	@Override
	public void hit(BraveKnight knight, Ogre ogre) {
		System.out.println("BraveKnight - " + " This sword is too broken to kill anyone");
	}
	
	@Override
	public void hit(LittleKnight knight, Ogre ogre) {
		System.out.println("LittleKnight - " + " This sword is too broken to kill anyone");
	}

}
