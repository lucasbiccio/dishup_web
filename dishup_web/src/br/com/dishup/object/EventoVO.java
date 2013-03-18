package br.com.dishup.object;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="evento")
public class EventoVO {

	@Id
	@Column(name = "id_evento")
	@SequenceGenerator(name = "id_evento", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_evento")
	private int id;
	
	@Column(name="nm_evento")
	private String nome;
	
	@Column(name="desc_evento")
	private String descricao;
	
	public EventoVO(){
		
	}

	public EventoVO(int id, String nome, String descricao) {
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
	}

	public int getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getDescricao() {
		return descricao;
	}
	
	@Override
	public String toString() {
		return "EVENTO ID("+id+") NOME("+nome+") DESCRICAO("+descricao+")";
	}
}
