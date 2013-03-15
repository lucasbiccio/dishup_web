package br.com.dishup.exception;

public class UsuarioNotFoundException extends Throwable {
	
	private static final long serialVersionUID = 1L;

	public UsuarioNotFoundException(String message){
		super(message);
	}

}
