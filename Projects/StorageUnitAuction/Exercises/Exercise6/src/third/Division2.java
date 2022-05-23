package third;

public class Division2 {
	public static void main(String[] args) {
		try {
			System.out.println(Integer.parseInt(args[0]) / Integer.parseInt(args[1]));
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Insufficient number of parameters.");
		} catch (NumberFormatException e) {
			System.out.println("Incorrect prameter format.");  
		} catch (ArithmeticException e) {
			System.out.println("Division error.");  
		}
	}
}
