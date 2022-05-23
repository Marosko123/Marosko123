public class Thread3 {
	int n = 0;
	
	public Thread3(int n) {
		this.n = n;
	}
	
	public void doIt() {
		for (int i = 0; i < 10; i++) {
			System.out.println("T" + n + ": " + i);
		}
	}
	
	public static void main(String[] args) {
		for (int i = 0; i < 5; i++) {
			Thread3 thread = new Thread3(i);
			new Thread(thread::doIt).start();
		}
	}
}
