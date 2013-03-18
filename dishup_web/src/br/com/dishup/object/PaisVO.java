package br.com.dishup.object;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @version 1.0 Class responsible for encapsulate the Pais object
 * @since 21/01/2013
 * @author Lucas Biccio Ribeiro
 */
@Entity
@Table(name="pais")
public class PaisVO {
	
	@Id
	@Column(name = "id_pais")
	@SequenceGenerator(name = "id_pais", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_pais")
	private int id;
	
	@Column(name="sigla_pais")
	private String sigla;
	
	@Column(name="nm_pais")
	private String nome;
	
	public PaisVO() {
	}
	
	public PaisVO(int id, String sigla, String nome) {
		this.id = id;
		this.sigla = sigla;
		this.nome = nome;
	}
	
	public int getId() {
		return id;
	}
	
	public String getSigla(){
		return sigla;
	}
	
	public String getNome() {
		return nome;
	}
	
	@Override
	public String toString() {
		return "PAIS: ID("+getId()+") SIGLA("+getSigla()+") NOME("+getNome()+")";
	}
	
	
}
