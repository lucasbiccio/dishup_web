package br.com.dishup.object;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**************************
 * @author Lucas Biccio Ribeiro
 * @since 06/02/2013
 * @version 1.0
 * Class responsible for encapsulate the object TipoUsuario.
 * This object represents a code data that indicates a type of system's user, as Consumidor or a Restaurante.
 *************************/
@Entity
@Table(name="tipo_usuario")
public class TipoUsuarioVO {
	
	@Id
	@Column(name = "id_tipo_usuario")
	@SequenceGenerator(name = "id_tipo_usuario", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_tipo_usuario")
	private int idTipoUsuario;
	
	@Column(name="nm_tipo_usuario")
	private String nomeTipoUsuario;
	
	@Column(name="desc_tipo_usuario")
	private String descTipoUsuario;
	
	public TipoUsuarioVO(){
		
	}
	
	public TipoUsuarioVO(int idTipoUsuario, String nomeTipoUsuario, String descTipoUsuario) {
		this.idTipoUsuario = idTipoUsuario;
		this.nomeTipoUsuario = nomeTipoUsuario;
		this.descTipoUsuario = descTipoUsuario;
	}

	/***********************
	 * Getter Methods
	 ***********************/
	
	public int getIdTipoUsuario() {
		return idTipoUsuario;
	}

	public String getNomeTipoUsuario() {
		return nomeTipoUsuario;
	}

	public String getDescTipoUsuario() {
		return descTipoUsuario;
	}

	@Override
	public String toString() {
		return "TipoUsuario: idTipoUsuario("+this.idTipoUsuario+") nomeTipoUsuario("+this.nomeTipoUsuario+") descTipoUsuario("+this.descTipoUsuario+")";
	}
}
