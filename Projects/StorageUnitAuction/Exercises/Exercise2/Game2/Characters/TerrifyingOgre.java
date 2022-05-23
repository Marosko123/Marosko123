package Characters;
public class TerrifyingOgre extends BadOgre{

	public TerrifyingOgre(int energy) {
		super(energy);
	}
	
	void roar() {
		System.out.println("BUBUBU");
	}
	
	@Override
	void eat(Knight knight) {
		super.eat(knight);
		roar();
	}

}
