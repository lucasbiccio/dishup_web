package br.com.dishup.environment;

import br.com.dishup.exception.DishUpException;

/**********************************
 * @author Lucas Biccio Ribeiro
 * @since 02/02/2013
 * @version 1.0 Class responsible for load the geographic components at system's environment.
 **********************************/
public class LoadAreaGeografica {
	
	private final String filePathPais = "config//geographic//pais.txt";
	private final String filePathEstado = "config//geographic//estado.txt";
	private final String filePathCidade = "config//geographic//cidade.txt";
	
	public void loadAreaGeografica() throws DishUpException{
		try{
			LoadPaisEnvironment loadPaisEnvironment = new LoadPaisEnvironment();
			loadPaisEnvironment.carregaPais(filePathPais);
			LoadEstadoEnvironment loadEstadoEnvironment = new LoadEstadoEnvironment();
			loadEstadoEnvironment.carregaEstado(filePathEstado);
			LoadCidadeEnvironment loadCidadeEnvironment = new LoadCidadeEnvironment();
			loadCidadeEnvironment.carregaCidade(filePathCidade);
		}catch(Throwable e){
			throw new DishUpException(this.getClass().getName(), "loadAreaGeografica()", null, e.getMessage(), e);
		}
	}
	
	public static void main(String[] args) {
		try{
			LoadAreaGeografica carregaAreaGeografica = new LoadAreaGeografica();
			carregaAreaGeografica.loadAreaGeografica();
		}catch(DishUpException e){
			System.out.println(e.toString());
		}
	}
	
}
