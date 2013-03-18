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

/*********************************
 * @author Lucas Biccio Ribeiro
 * @since 21/01/2013
 * @version 1.0 Class responsible for encapsulate the methods of Estado object (geographic organization)
 *********************************/

@Entity
@Table(name="estado")
public class EstadoVO {

	@Id
	@Column(name = "id_estado")
	@SequenceGenerator(name = "id_estado", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_estado")
	private int id;
	
	@Column(name="sigla_estado")
	private String sigla;
	
	@Column(name="nm_estado")
	private String nome;
	
	@OneToOne
	@JoinColumn(name="id_pais")
	private PaisVO pais;

	public EstadoVO() {
	}
	
	public EstadoVO(int id, String sigla, String nome, PaisVO pais) {
		this.id = id;
		this.sigla = sigla;
		this.nome = nome;
		this.pais = pais;
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

	public PaisVO getPais() {
		return pais;
	}
	
	@Override
	public String toString() {
		return "ESTADO: ID("+getId()+") SIGLA("+getSigla()+") NOME("+getNome()+") PAIS("+getPais().toString()+")";
	}
}
