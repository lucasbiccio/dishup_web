package br.com.dishup.object;

/**
 * @version 1.0 Class responsible for encapsulate the Cidade object
 * @author Lucas Biccio Ribeiro
 * @since 21/01/2013
 */
public class CidadeVO {
	
	/***************************
	*       ATRIBUTOS 
	****************************/
	private int id;
	private String nome;
	private EstadoVO estado;
	private PaisVO pais;
	
	/***************************
	*       CONSTRUTORES 
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
	*       METODOS GET 
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
	*       METODOS OVERRIDE 
	****************************/
	@Override
	public String toString() {
		return "CIDADE: ID("+getId()+") NOME("+getNome()+") ESTADO("+getEstado().toString()+") PAIS("+getPais().toString()+")";
	}
}
