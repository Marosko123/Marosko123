package Characters;

public class BadOgre extends Ogre {
	boolean hungry; // beware of attribute hiding!
	
	public BadOgre(int energy) {
		super(energy);
	}
	
	void revenge(Knight knight) {
		if (isHungry())
			eat(knight);
	}
	void eat(Knight knight) {
		knight.setEnergy(0);
	}
}
