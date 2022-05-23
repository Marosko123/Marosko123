package application;

import auctionClasses.Auction;
import storageUnits.CommonUnit;

/**
 * Part of the OBSERVER PATTERN
 * abstract Class that object implements
 * @author Maroš
 * 
 */
public abstract class AuctionObserver {
	protected Auction auction;
	protected CommonUnit unit;
	public abstract void update();
}
