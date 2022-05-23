public class Thread5 implements Runnable{
	public static void main(String[] args) {
		for (int i = 0; i < 5; i++) {
			final int n = i; // anonymous classes can access only final variables
			
			new Thread(new Thread5()).start();
		}
	}

	@Override
	public void run() {
		for(int i=0; i<10; i++)
			System.out.println("T =  " + i);
		
	}
}

