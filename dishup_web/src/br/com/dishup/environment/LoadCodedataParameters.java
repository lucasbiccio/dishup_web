package br.com.dishup.environment;

import br.com.dishup.exception.DishUpException;

public class LoadCodedataParameters {
	
	private final String filePathCanal = "config//codedata//canal.txt";
	private final String filePathTipoUsuario = "config//codedata//tipoUsuario.txt";
	private final String filePathStatusUsuario = "config//codedata//statusUsuario.txt";
	private final String filePathEvento = "config//codedata//evento.txt";
	
	public void loadCodedata() throws DishUpException{
		try{
			LoadCanalEnvironment loadCanal = new LoadCanalEnvironment();
			loadCanal.carregaCanal(filePathCanal);
			LoadTipoUsuario loadTipoUsuario = new LoadTipoUsuario();
			loadTipoUsuario.carregaTipoUsuario(filePathTipoUsuario);			
			LoadStatusUsuario loadStatusUsuario = new LoadStatusUsuario();
			loadStatusUsuario.carregaStatusUsuario(filePathStatusUsuario);
			LoadEventoEnvironment loadEvento = new LoadEventoEnvironment();
			loadEvento.carregaEvento(filePathEvento);
		}catch(Throwable e){
			throw new DishUpException(this.getClass().getName(), "loadCodedata()", null, e.getMessage(), e);
		}
	}
	
	public static void main(String[] args) {
		try{
			LoadCodedataParameters l = new LoadCodedataParameters();
			l.loadCodedata();
		}catch(DishUpException e){
			System.out.println(e.toString());
		}
	}
}
