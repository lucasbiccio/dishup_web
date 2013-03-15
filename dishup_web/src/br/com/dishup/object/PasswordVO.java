package br.com.dishup.object;

public class PasswordVO {
	
	private int idUsuario;
	private String assinatura;
	
	public PasswordVO() {
	}

	public PasswordVO(int idUsuario, String assinatura) {
		this.idUsuario = idUsuario;
		this.assinatura = assinatura;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public String getAssinatura() {
		return assinatura;
	}
}
