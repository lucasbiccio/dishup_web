package br.com.dishup.exception;

public class TableUniqueViolationException extends Throwable{
	
	private static final long serialVersionUID = 1L;

	public TableUniqueViolationException(String message) {
		super(message);
	}

}
