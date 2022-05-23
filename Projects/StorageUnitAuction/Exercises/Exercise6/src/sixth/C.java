package sixth;

// Client code
public class C {
	public static void main(String[] args) {
		A a = D.getMyObject(); // the client code works through the interface of the A class, but an object of a derived class can occur there, too
		
		try {
			a.m();
		} catch (MyException e) { // the interface of the A class, in the m() method, it is sufficient to handle only MyException
			/*...*/
		}						// the client code has no way of knowing about derived classes, such as D
	}
}
