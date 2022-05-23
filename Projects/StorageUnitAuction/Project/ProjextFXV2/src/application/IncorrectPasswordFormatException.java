package application;

/**
 * User defined exception class
 * @author Maroš
 *
 */
public class IncorrectPasswordFormatException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int errCode;

	/**
	 * returns newly defined errorcode
	 * @param errCode
	 */
	public IncorrectPasswordFormatException(int errCode) {
		super("Invalid password");
		this.errCode = errCode;
	}

	/**
	 * returns String containing original error messages
	 */
	@Override
	public String getMessage() {
		switch(errCode) {
		case 1: return "Password must be at least\n 6 characters long...";
		case 2: return "Password must contain\n at least 1 digit...";
		case 3: return "Password must not \ncontain a space...";
		case 4: return "Password must contain at\n least 1 lowercase letter...";
		case 5: return "Password must contain at\n least 1 capital letter...";
		case 6: return "Password must contain at\n least 1 special character...";  //  @, #, %, &, !, $
		default: return "Unknown error code...";
		}
	}
	
}
