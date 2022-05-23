package gui;

import game.*;
import javafx.scene.control.*;
import javafx.application.*;


public class EnergyOfWarriors extends TextField implements ClashFollower {
	private Game game;
	private int energy;

	public EnergyOfWarriors(Game game) {	
		super();
		this.game = game;
	}
	
	public void inform() {
		energy = game.knightsEnergy() + game.ogresEnergy();

//		setText(Integer.toString(energy));
		// inevitable in using another thread to realize a clash because this way this method will be realized outside the JavaFX event thread
		// problems might start occurring only with higher counts of warriors (Exception in thread "Thread-3" java.lang.NullPointerException...)
		Platform.runLater(() -> setText(Integer.toString(energy)));
	}
}
