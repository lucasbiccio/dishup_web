package br.com.dishup.exception;

public class StatusUsuarioNotFoundException extends Throwable{
	
	private static final long serialVersionUID = 1L;

	public StatusUsuarioNotFoundException(String message){
		super(message);
	}

}
