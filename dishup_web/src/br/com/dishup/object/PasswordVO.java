package br.com.dishup.object;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="autenticacao_usuario")
public class PasswordVO {
	
	@OneToOne
	@JoinColumn(name="id_usuario")
	private UsuarioVO usuario;
	
	@Column(name="assinatura_usuario")
	private String assinatura;
	
	public PasswordVO() {
	}

	public PasswordVO(UsuarioVO usuario, String assinatura) {
		this.usuario = usuario;
		this.assinatura = assinatura;
	}

	public UsuarioVO getUsuarioVO() {
		return usuario;
	}

	public String getAssinatura() {
		return assinatura;
	}
}
