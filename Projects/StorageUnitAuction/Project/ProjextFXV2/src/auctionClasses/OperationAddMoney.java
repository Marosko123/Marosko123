package auctionClasses;

/**
 * Part of the STRATEGY PATTERN
 * It creates an operation addition
 * @author Maroš
 *
 */
public class OperationAddMoney implements StrategyAddSubMoney {

	/**
	 * @return it returns the addition of a to b
	 */
	@Override
	public int doOperation(int a, int b) {
		System.out.println("STRATEGY PATTERN: adding money to the user...");
		return a+b;
	}

}
