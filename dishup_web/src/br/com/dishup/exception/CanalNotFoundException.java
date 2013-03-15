package br.com.dishup.exception;

public class CanalNotFoundException extends Throwable {
	
	private static final long serialVersionUID = 1L;

	public CanalNotFoundException(String message){
		super(message);
	}
}
