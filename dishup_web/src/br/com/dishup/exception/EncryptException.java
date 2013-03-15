package br.com.dishup.exception;

public class EncryptException extends Throwable{
	
	private static final long serialVersionUID = 1L;

	public EncryptException(String message){
		super(message);
	}
}
