
public class VeryBadOgre extends BadOgre {

	public VeryBadOgre() {
	}

	@Override
	void revenge(Knight knight) {
		super.revenge(knight);
		System.out.println("You are super dead, Knight...");
	}
}
