package auctionClasses;

/**
 * interface that informs all objects which are implementing it
 * @author Maro�
 *
 */
public interface AuctionFollower {
	void inform();
	default void displaySuccess() {
		System.out.println("Unit was created successfully");
	}
}
