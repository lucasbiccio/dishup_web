package br.com.dishup.exception;

/*********************************
 * @author Lucas Biccio Ribeiro
 * @since 17/02/2013
 * @version 1.0 Class responsible for encapsulate the exception when a TipoUsuario already exists in system´s database
 *********************************/
public class TipoUsuarioAlreadyExistException extends Throwable {
	
	private static final long serialVersionUID = 1L;

	/****************
	 * Constructor
	 * @param message - Exception Message
	 ****************/
	public TipoUsuarioAlreadyExistException(String message){
		super(message);
	}

}
