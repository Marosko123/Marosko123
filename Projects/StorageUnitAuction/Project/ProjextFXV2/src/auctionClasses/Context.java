package auctionClasses;

/**
 * Part of the STRATEGY PATTERN
 * it handles all strategies and is calling actual function that is about to be executed
 * @author Maroš
 */
public class Context {
	private StrategyAddSubMoney strategy;

	/**
	 * Constructor which sets used strategy
	 * @param strategy used strategy
	 */
	public Context(StrategyAddSubMoney strategy) {
		this.strategy = strategy;
	}
	
	/**
	 * function that calls current strategy
	 * @param a first amount
	 * @param b second amount
	 * @return result of operation a ? b
	 */
	public int executeStrategy(int a, int b) {
		return strategy.doOperation(a, b);
	}
}
