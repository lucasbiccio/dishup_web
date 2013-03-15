package br.com.dishup.exception;

public class EventoNotFoundException extends Throwable{
	
	private static final long serialVersionUID = 1L;

	public EventoNotFoundException(String message){
		super(message);
	}

}
