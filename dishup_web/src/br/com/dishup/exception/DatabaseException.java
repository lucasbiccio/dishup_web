package br.com.dishup.exception;

public class DatabaseException extends Throwable{
	
	private static final long serialVersionUID = 1L;

	public DatabaseException(String message){
		super(message);
	}

}
