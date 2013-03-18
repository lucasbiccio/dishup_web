package br.com.dishup.object;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity @Table(name = "canal")
public class CanalVO {
	
	@Id @Column
	@SequenceGenerator(name = "id_canal", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_canal")
	private Integer id;
	
	@Column(name = "nm_canal")
	private String nome;
	
	@Column(name = "desc_canal")
	private String descricao;
	
	public CanalVO() {
		
	}

	public CanalVO(int id, String nome, String descricao) {
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
		return "CANAL: ID("+getId()+") NOME("+getNome()+") DESCRICAO("+getDescricao()+")";
	}
	
}
