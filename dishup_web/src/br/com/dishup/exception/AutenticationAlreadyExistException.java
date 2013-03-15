package br.com.dishup.exception;

public class AutenticationAlreadyExistException extends Throwable {
	
	private static final long serialVersionUID = 1L;

	public AutenticationAlreadyExistException (String message){
		super(message);
	}
}
