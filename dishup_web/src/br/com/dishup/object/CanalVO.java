package br.com.dishup.object;

public class CanalVO {
	
	private int id;
	private String nome;
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
