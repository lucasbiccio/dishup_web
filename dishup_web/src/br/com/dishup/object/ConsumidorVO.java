package br.com.dishup.object;

public class ConsumidorVO {
	
	private UsuarioVO usuario;
	private DadosBasicoConsumidorVO dadosBasicoConsumidor;
	
	public ConsumidorVO(){
		
	}
	
	public ConsumidorVO(UsuarioVO usuario,
			DadosBasicoConsumidorVO dadosBasicoConsumidor) {
		this.usuario = usuario;
		this.dadosBasicoConsumidor = dadosBasicoConsumidor;
	}

	public UsuarioVO getUsuario() {
		return usuario;
	}

	public DadosBasicoConsumidorVO getDadosBasicoConsumidor() {
		return dadosBasicoConsumidor;
	}
}
