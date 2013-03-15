package br.com.dishup.exception;

public class StatusUsuarioAlreadyExistException extends Throwable {

	private static final long serialVersionUID = 1L;

	public StatusUsuarioAlreadyExistException(String message){
		super(message);
	}
}
