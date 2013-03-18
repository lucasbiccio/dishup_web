package br.com.dishup.object;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**********************************
 * @author Lucas Biccio Ribeiro
 * @since 06/02/2013
 * @version 1.0
 * Class responsible for encapsulate the object Status.
 * This object represents a code data that indicates a type of system's status for an user, as active, blocked and deleted.
 **********************************/
@Entity
@Table(name="status_usuario")
public class StatusUsuarioVO {
	
	@Id
	@Column(name = "id_status_usuario")
	@SequenceGenerator(name = "id_status_usuario", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_cidade")
	private int idStatusUsuario;
	
	@Column(name="nm_status_usuario")
	private String nomeStatusUsuario;
	
	@Column(name="desc_status_usuario")
	private String descStatusUsuario;
	
	
	public StatusUsuarioVO(){
		
	}

	public StatusUsuarioVO(int idStatusUsuario, String nomeStatusUsuario, String descStatusUsuario) {
		this.idStatusUsuario = idStatusUsuario;
		this.nomeStatusUsuario = nomeStatusUsuario;
		this.descStatusUsuario = descStatusUsuario;
	}
	

	public int getIdStatus() {
		return idStatusUsuario;
	}

	public String getNomeStatus() {
		return nomeStatusUsuario;
	}

	public String getDescStatus() {
		return descStatusUsuario;
	}
	
	
	@Override
	public String toString() {
		return "StatusUsuario: idStatusUsuario("+idStatusUsuario+") nomeStatusUsuario("+nomeStatusUsuario+") descStatusUsuario("+descStatusUsuario+") ";
	}
}
