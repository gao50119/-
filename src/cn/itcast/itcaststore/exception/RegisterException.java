package cn.itcast.itcaststore.exception;

public class RegisterException extends Exception{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RegisterException() {
    	
    }
    
    public RegisterException(String err) {
    	super(err);
    }
}
