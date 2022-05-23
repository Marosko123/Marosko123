
public class StrongKnight extends Knight{

	public StrongKnight() {
			
	}

	@Override
	void attack(Ogre ogre) {
		ogre.energy = (int) (0.5 * ogre.energy); // (int) just casts a float result into an int
		ogre.revenge(this); // this represents a reference to the current knight object 
		System.out.println(this);
	}
}
