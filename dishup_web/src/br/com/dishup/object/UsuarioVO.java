package br.com.dishup.object;

public class UsuarioVO {
	
	private int id;
	private String email;
	private TipoUsuarioVO tipoUsuario;
	private StatusUsuarioVO statusUsuario;
	private String dataInclusao;
	private String dataUltimaAlteracao;
	private String dataUltimaAlteracaoSenha;
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
