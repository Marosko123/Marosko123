package slle;

public class Main {
	public static void main(String... args) {
		SLList<Integer> l = new SLList<Integer>();

		for (int i = 1; i < 10; i++)
			l.tailInsert(i);
	
		l.print();
	}

}
