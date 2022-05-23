package sixth;
import java.util.*;

public class D {
	static A getMyObject() {
		Random r = new Random();
		if (r.nextInt(100) < 50)
			return new A();
		else
			return new B();
	}
}
