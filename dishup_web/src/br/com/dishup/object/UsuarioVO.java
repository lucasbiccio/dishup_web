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


@Entity
@Table(name ="usuario")
public class UsuarioVO {
	
	@Id
	@Column(name="id_usuario")
	@SequenceGenerator(name = "id_usuario", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_usuario")
	private int id;
	
	@Column(name="email_usuario")
	private String email;
	
	@OneToOne
	@JoinColumn(name = "id_tipo_usuario")
	private TipoUsuarioVO tipoUsuario;
	
	@OneToOne
	@JoinColumn(name = "id_status_usuario")
	private StatusUsuarioVO statusUsuario;
	
	@Column(name="dt_inclusao_usuario")
	private String dataInclusao;
	
	@Column(name="dt_ultima_alteracao_usuario")
	private String dataUltimaAlteracao;
	
	@Column(name="dt_ultima_alteracao_senha_usuario")
	private String dataUltimaAlteracaoSenha;
	
	@Column(name="flag_solicitacao_alteracao_senha_usuario")
	private boolean flagSolicitacaoAlteracaoSenha;
	
	public UsuarioVO(){
		
	}
	
	public UsuarioVO(String email, TipoUsuarioVO tipoUsuario,
			StatusUsuarioVO statusUsuario, String dataInclusao,
			String dataUltimaAlteracao, String dataUltimaAlteracaoSenha,
			boolean flagSolicitacaoAlteracaoSenha) {
		this.email = email;
		this.tipoUsuario = tipoUsuario;
		this.statusUsuario = statusUsuario;
		this.dataInclusao = dataInclusao;
		this.dataUltimaAlteracao = dataUltimaAlteracao;
		this.dataUltimaAlteracaoSenha = dataUltimaAlteracaoSenha;
		this.flagSolicitacaoAlteracaoSenha = flagSolicitacaoAlteracaoSenha;
	}

	public UsuarioVO(int id, String email, TipoUsuarioVO tipoUsuario,
			StatusUsuarioVO statusUsuario, String dataInclusao,
			String dataUltimaAlteracao, String dataUltimaAlteracaoSenha,
			boolean flagSolicitacaoAlteracaoSenha) {
		this.id = id;
		this.email = email;
		this.tipoUsuario = tipoUsuario;
		this.statusUsuario = statusUsuario;
		this.dataInclusao = dataInclusao;
		this.dataUltimaAlteracao = dataUltimaAlteracao;
		this.dataUltimaAlteracaoSenha = dataUltimaAlteracaoSenha;
		this.flagSolicitacaoAlteracaoSenha = flagSolicitacaoAlteracaoSenha;
	}

	public int getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public TipoUsuarioVO getTipoUsuario() {
		return tipoUsuario;
	}

	public StatusUsuarioVO getStatusUsuario() {
		return statusUsuario;
	}

	public String getDataInclusao() {
		return dataInclusao;
	}

	public String getDataUltimaAlteracao() {
		return dataUltimaAlteracao;
	}

	public String getDataUltimaAlteracaoSenha() {
		return dataUltimaAlteracaoSenha;
	}

	public boolean isFlagSolicitacaoAlteracaoSenha() {
		return flagSolicitacaoAlteracaoSenha;
	}
	
	@Override
	public String toString() {
		return "USUARIO: ID("+id+") EMAIL("+email+") TIPO USUARIO:("+tipoUsuario.toString()+") STATUS USUARIO: " +
				"("+statusUsuario.toString()+") DATA INCLUSAO("+dataInclusao+") DATA ULTIMA ALTERACAO("+dataUltimaAlteracao+") " +
				"DATA ULTIMA ALTERACAO SENHA("+dataUltimaAlteracaoSenha+") FLAG SOLICITACAO ALTERACAO SENHA("+flagSolicitacaoAlteracaoSenha+")";
	}
}
