package br.com.dishup.object;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="dados_basicos_consumidor")
public class DadosBasicoConsumidorVO {
	
	@OneToOne
	@Column(name="id_usuario")
	private UsuarioVO usuario;
	
	@Column(name="nome_usuario")
	private String nome;
	
	@Column(name="sexo")
	private char sexo;
	
	@Column(name="flag_interesse_restaurante")
	private boolean flagInteresseRestauranteFavorito;
	
	@Column(name="flag_interesse_info_promocao")
	private boolean flagInteresseInfoPromocao;
	
	@Column(name="flag_interesse_info_dishup")
	private boolean flagInteresseDishUp;
	
	public DadosBasicoConsumidorVO(){
		
	}
	
	public DadosBasicoConsumidorVO(String nome, char sexo,
			boolean flagInteresseRestauranteFavorito,
			boolean flagInteresseInfoPromocao, boolean flagInteresseDishUp) {
		this.nome = nome;
		this.sexo = sexo;
		this.flagInteresseRestauranteFavorito = flagInteresseRestauranteFavorito;
		this.flagInteresseInfoPromocao = flagInteresseInfoPromocao;
		this.flagInteresseDishUp = flagInteresseDishUp;
	}
	
	public String getNome(){
		return nome;
	}
	
	public char getSexo() {
		return sexo;
	}
	
	public boolean isFlagInteresseRestauranteFavorito() {
		return flagInteresseRestauranteFavorito;
	}
	
	public boolean isFlagInteresseInfoPromocao() {
		return flagInteresseInfoPromocao;
	}
	
	public boolean isFlagInteresseDishUp() {
		return flagInteresseDishUp;
	}
}
