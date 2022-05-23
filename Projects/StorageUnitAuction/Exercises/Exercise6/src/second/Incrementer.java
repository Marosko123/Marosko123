package second;

public class Incrementer extends Thread {
	public void run() {
		Values values = new Values();
		for (int i = 0; i < Integer.MAX_VALUE; i++) {
			values.increment();
		}
	}
}
