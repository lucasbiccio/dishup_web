package br.com.dishup.codedata;

/********************************
 * @author Lucas Biccio Ribeiro
 * @since 27/02/2013
 * @version 1.0 Enum responsible for encapsulate the system s user s status code data.
 ********************************/
public enum StatusUsuarioCD {
	
	/******************
	 * User s status constants.
	 ******************/
	ACTIVE                   (1, "Ativo"),
	BLOCKED                  (2, "Bloqueado"),
	EXCLUDED                 (3, "Excluido"),
	PENDING_CHANGE_PASSWORD  (4, "Pendente troca de senha"),
	EXPIRED_PASSWORD         (5, "Senha expirada"),
	ACTIVATION_PENDING       (6, "Pendente de Ativacao");
	
	/**************************
	 * User s status attributes
	 **************************/
	private final int id;
	private final String name;
	
	/**************************
	 * Method responsible for get the user s status id.
	 * @return user s type id
	 **************************/
	public int getId() {
		return id;
	}
	
	/**************************
	 * Method responsible for get the user s status name.
	 * @return user s type id
	 **************************/
	public String getName() {
		return name;
	}
	
	/**************************
	 * Constructor
	 * @param id
	 * @param name
	 **************************/
	private StatusUsuarioCD(int id, String name){
		this.id = id;
		this.name = name;
	}

}
