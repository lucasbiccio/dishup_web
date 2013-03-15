package br.com.dishup.codedata;

/********************************
 * @author Lucas Biccio Ribeiro
 * @since 27/02/2013
 * @version 1.0 Enum responsible for encapsulate the system큦 user s type code data.
 ********************************/
public enum TipoUsuarioCD {

	/******************
	 * User큦 type constants.
	 ******************/
	CONSUMIDOR             (1, "Consumidor"),
	RESTAURANTE            (2, "Restaurante"),
	RESTAURANTE_MASTER     (3, "Restaurante - Usuario Master"),
	DISHUP                 (4, "Dish Up");
	
	/**************************
	 * User s type attributes
	 **************************/
	private final int id;
	private final String nome;
	
	/**************************
	 * Method responsible for get the user큦 type id.
	 * @return user s type id
	 **************************/
	public int getId() {
		return id;
	}
	
	/**************************
	 * Method responsible for get the user큦 type name
	 * @return user s type name
	 **************************/
	public String getNome() {
		return nome;
	}
	
	/*************************
	 * Constructor
	 * @param id - user s type id
	 * @param nome - user s type name
	 *************************/
	private TipoUsuarioCD(int id, String nome){
		this.id = id;
		this.nome = nome;
	}
}
