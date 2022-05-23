public class Thread4 {
	int n = 0;
	
	public Thread4(int n) {
		this.n = n;
	}
	
	public void doIt() {
		for (int i = 0; i < 10; i++) {
			System.out.println("T" + n + ": " + i);
		}
	}
	
	public static void main(String[] args) {
		for (int i = 0; i < 5; i++) {
			Thread4 thread = new Thread4(i);
			new Thread(() -> thread.doIt()).start();
		}
	}
}
