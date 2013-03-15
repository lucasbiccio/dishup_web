package br.com.dishup.exception;

public class CanalAlreadyExistException extends Throwable{
	
	private static final long serialVersionUID = 1L;

	public CanalAlreadyExistException(String message){
		super(message);
	}
}
