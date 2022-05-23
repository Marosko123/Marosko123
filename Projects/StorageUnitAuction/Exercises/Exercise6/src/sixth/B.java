package sixth;

public class B extends A {
	void m() throws MySubexception { // the compiler won't allow this 
		/* ... */				// the client code (which is in the C class) would be invalidated by introducing another exception not derived from an already declared one
	}
}
