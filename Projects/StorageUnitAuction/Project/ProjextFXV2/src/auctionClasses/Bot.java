package auctionClasses;

import java.util.Random;
/**
 * Class extended from the User class
 * It contains more information, such as Bots iq and artificial brain
 */
public class Bot extends User {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * nested Class creating the bots brain
	 * it decides if bot is smart or not
	 */
	static class Brain{
		private int iq;
		private boolean isSmart;
		
		/**
		 * constructor which creates current users brain with given parameters
		 * @param iq iq is inteligent quotient of the current bot. It is generated automatically
		 */
		public Brain(int iq) {
			this.iq = iq;
			isSmart = iq >= 100;
		}

		/**
		 * @return function to return an IQ of the current bot
		 */
		public int getIq() {
			return iq;
		}
		
		/**
		 * @return function returns true if bot is smart, false if is not smart
		 */
		public boolean getIsSmart() {
			return isSmart;
		}
	}
	
	/**
	 * Constructor of the bot Class
	 * @param name name of the bot
	 * @param password password of the bot, should be empty
	 * @param balance money balance of the bot
	 * @param unitsBought number of bought units, starts with 0	 
	 * */
	public Bot(String name, String password, int balance, int unitsBought, Auction auction) {
		super(name, password, balance, unitsBought, auction);
	}
	
	/**
	 * @return returns boolean value true if bot wants to bid, false if doesnt want to bid
	 * @param greedLevel greedLevel higher the amount, higher the chance to bid
	 * @param unitPrice unitPrice contains an information about the current price of the actual unit
	 */
	public boolean wantsToBit(int greedLevel, int unitPrice) {
		Random rnd = new Random();
		Brain iq = new Brain(rnd.nextInt(150));
		
		if(getBalance() >= unitPrice) {
			if(iq.getIsSmart()) {
				if(rnd.nextInt(greedLevel) > 17) {
					System.out.println("Smart bot bids...");
					return true;
				}
			}
			else {
				if(rnd.nextInt(greedLevel) > 12) {
					System.out.println("Average bot bids...");
					return true;
				}
			}
		}
		return false;
	}
}
