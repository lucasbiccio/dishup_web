package br.com.dishup.exception;

public class UsuarioAlreadyExistException extends Throwable{
	
	private static final long serialVersionUID = 1L;

	public UsuarioAlreadyExistException(String message){
		super(message);
	}

}
