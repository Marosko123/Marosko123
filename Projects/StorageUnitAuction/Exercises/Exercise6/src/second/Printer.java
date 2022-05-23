package second;

public class Printer extends Thread {
	public void run() {
		Values values = new Values();
		for (int i = 0; i < Integer.MAX_VALUE; i++) {
			values.print();
		}
	}
}
