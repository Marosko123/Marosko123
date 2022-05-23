package game;

import characters.BadOgre;
import characters.BraveKnight;
import characters.BrokenSword;
import characters.IronSword;
import characters.Knight;
import characters.LightSword;
import characters.LittleKnight;
import characters.Ogre;

public class SenselessGameSetup implements GameSetup {
	@Override
	public void setup(Knight[] knights, Ogre[] ogres, int nKnights, int nBraveKnights, int nBadOgres) {
		System.out.println("IDK WHAT AM I DOING INSIDE HERE");
		int numberOfWarriors = nKnights + nBraveKnights;
		for (int i = 0; i < 2; ++i){
			knights[i] = new Knight(80, new IronSword(i));
		}
		
		for (int i = 2; i < nKnights; ++i){
			knights[i] = new LittleKnight(50, new BrokenSword(i));
		}

		for (int i = nKnights; i < numberOfWarriors; ++i){
			knights[i] = new BraveKnight(100, new LightSword(i));
		}
		
		for (int i = nKnights; i < numberOfWarriors; ++i){
			knights[i] = new BraveKnight(100, new LightSword(i));
		}

		for (int i = 0; i < nBadOgres; ++i){
			ogres[i] = new BadOgre(100);
		}
		
		for (int i = nBadOgres; i < numberOfWarriors; ++i){
			ogres[i] = new Ogre(100);
		}
	}

}
