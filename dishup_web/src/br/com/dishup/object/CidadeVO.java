package br.com.dishup.object;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @version 1.0 Class responsible for encapsulate the Cidade object
 * @author Lucas Biccio Ribeiro
 * @since 21/01/2013
 */
@Entity
@Table(name = "cidade")
public class CidadeVO {

	/***************************
	 * ATRIBUTOS
	 ****************************/
	@Id
	@Column(name = "id_cidade")
	@SequenceGenerator(name = "id_cidade", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_cidade")
	private int id;

	@Column(name = "nm_cidade")
	private String nome;

	@OneToOne
	@JoinColumn(name = "id_estado")
	private EstadoVO estado;
	
	@OneToOne
	@JoinColumn(name = "id_pais")
	private PaisVO pais;

	/***************************
	 * CONSTRUTORES
	 ****************************/
	public CidadeVO() {
	}

	public CidadeVO(int id, String nome, EstadoVO estado, PaisVO pais) {
		this.id = id;
		this.nome = nome;
		this.estado = estado;
		this.pais = pais;
	}

	/***************************
	 * METODOS GET
	 ****************************/
	public int getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public EstadoVO getEstado() {
		return estado;
	}

	public PaisVO getPais() {
		return pais;
	}

	/***************************
	 * METODOS OVERRIDE
	 ****************************/
	@Override
	public String toString() {
		return "CIDADE: ID(" + getId() + ") NOME(" + getNome() + ") ESTADO("
				+ getEstado().toString() + ") PAIS(" + getPais().toString()
				+ ")";
	}
}
