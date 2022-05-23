package auctionClasses;

/**
 * Part of the STRATEGY PATTERN
 * It creates an operation subtraction
 * @author Maroš
 *
 */
public class OperationSubMoney implements StrategyAddSubMoney{

	/**
	 * @return returns the subtraction of the values a and b
	 */
	@Override
	public int doOperation(int a, int b) {
		System.out.println("STRATEGY PATTERN: subtracting money from the user...");
		return a-b;
	}
}
