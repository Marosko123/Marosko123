public class Thread2 implements Runnable {
	int n = 0;
	
	public Thread2(int n) {
		this.n = n;
	}
	
	public void run() {
		for (int i = 0; i < 10; i++) {
			System.out.println("T" + n + ": " + i);
		}
	}
	
	public static void main(String[] args) {
		System.out.println("IDE");
		for (int i = 0; i < 5; i++)
			new Thread(new Thread2(i)).start();
	}
}
