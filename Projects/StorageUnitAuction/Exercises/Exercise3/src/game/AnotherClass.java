package game;

import java.util.Random;

public class AnotherClass implements EnergyObserver{

	public AnotherClass() {
	}

	@Override
	public void printEnergy() {
		System.out.println("Random ENERGY from AnotherClass: " + new Random().nextInt(200));
	}
}
