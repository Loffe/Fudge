package se.eloff.fudge.client;

public class LoginException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public LoginException() {
	}
	
	public LoginException(String errorMessage) {
		super(errorMessage);
	}
}
