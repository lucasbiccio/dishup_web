package br.com.dishup.object;

public class DadosBasicoConsumidorVO {
	
	private String nome;
	private char sexo;
	private boolean flagInteresseRestauranteFavorito;
	private boolean flagInteresseInfoPromocao;
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
