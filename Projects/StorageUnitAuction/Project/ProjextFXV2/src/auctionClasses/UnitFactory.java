package auctionClasses;

import java.util.Random;

import storageUnits.CommonUnit;
import storageUnits.EpicUnit;
import storageUnits.MysteriousUnit;
import storageUnits.RareUnit;

/**
 * Part of the FACTORY PATTERN
 * Class that generates storage units randomly 
 * @author Maroš
 *
 */
public class UnitFactory {
    private	Random rnd;

    /**
     * Constructor that creates random object rnd
     */
	public UnitFactory() {
		rnd = new Random();
	}
	
	/**
	 * function returns randomly created storage unit
	 * @return Common Unit
	 */
	public CommonUnit generateNewUnit() {
		CommonUnit unit;
		int generatedNumber = rnd.nextInt(0, 100); // 0 to 99
		
		if (generatedNumber < 50) unit = new CommonUnit();
		else if(generatedNumber < 75) unit = new RareUnit();
		else if(generatedNumber < 95) unit = new EpicUnit();
		else unit = new MysteriousUnit();
        unit.returnUnitType();
		
		return unit;
	}
}
