package game;

import java.util.Random;

import characters.*;

public class ReversedGameSetup implements GameSetup {
	public void setup(Knight[] knights, Ogre[] ogres, int nKnights, int nBraveKnights, int nBadOgres) {
		int numberOfWarriors = nKnights + nBraveKnights;
		
		for (int i = 0; i < nBraveKnights; ++i){
			knights[i] = new BraveKnight(100, new LightSword(i));
		}

		for (int i = nBraveKnights; i < numberOfWarriors; ++i){
			knights[i] = new Knight(80, new IronSword(i));
		}

		for (int i = 0; i < nBadOgres; ++i){
			ogres[i] = new BadOgre(100);
		}
		
		for (int i = nBadOgres; i < numberOfWarriors; ++i){
			ogres[i] = new Ogre(100);
		}
	}
	
	/*public void setup(Knight[] knights, Ogre[] ogres) {
		System.out.println("Setup without parameters...");
		Random rnd = new Random();
		
		int numOfWarriors = rnd.nextInt(100);
		int numOfOgres= rnd.nextInt(100);
		
		for(int i= 0; i<numOfWarriors/2; i++) {
			knights[i] = new BraveKnight(100, new LightSword(i));
			knights[i+numOfWarriors/2] = new Knight(80, new IronSword(i));			
		}
		
	}*/
}
