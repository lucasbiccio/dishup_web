package br.com.dishup.codedata;

public enum EventoCD {
	
	USER_SIGN_UP (1,"Cadastro Usuario");

	private final int id;
	private final String name;
	
	private EventoCD(int id, String name){
		this.name = name;
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
}
