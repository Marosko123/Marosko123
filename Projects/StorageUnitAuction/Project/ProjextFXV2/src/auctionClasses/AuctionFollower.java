package auctionClasses;

/**
 * interface that informs all objects which are implementing it
 * @author Maroš
 *
 */
public interface AuctionFollower {
	void inform();
	default void displaySuccess() {
		System.out.println("Unit was created successfully");
	}
}
