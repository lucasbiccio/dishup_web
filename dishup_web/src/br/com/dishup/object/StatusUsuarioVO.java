package br.com.dishup.object;

/**********************************
 * @author Lucas Biccio Ribeiro
 * @since 06/02/2013
 * @version 1.0
 * Class responsible for encapsulate the object Status.
 * This object represents a code data that indicates a type of system's status for an user, as active, blocked and deleted.
 **********************************/
public class StatusUsuarioVO {
	
	/***********************
	 * Attributes
	 ***********************/
	private int idStatusUsuario;
	private String nomeStatusUsuario;
	private String descStatusUsuario;
	
	/***********************
	 * Constructor
	 ***********************/
	
	public StatusUsuarioVO(){
		
	}

	public StatusUsuarioVO(int idStatusUsuario, String nomeStatusUsuario, String descStatusUsuario) {
		this.idStatusUsuario = idStatusUsuario;
		this.nomeStatusUsuario = nomeStatusUsuario;
		this.descStatusUsuario = descStatusUsuario;
	}
	
	/***********************
	 * Getter Methods
	 ***********************/

	public int getIdStatus() {
		return idStatusUsuario;
	}

	public String getNomeStatus() {
		return nomeStatusUsuario;
	}

	public String getDescStatus() {
		return descStatusUsuario;
	}
	
	/***********************
	 * Override Methods
	 ***********************/
	
	@Override
	public String toString() {
		return "StatusUsuario: idStatusUsuario("+idStatusUsuario+") nomeStatusUsuario("+nomeStatusUsuario+") descStatusUsuario("+descStatusUsuario+") ";
	}
}
