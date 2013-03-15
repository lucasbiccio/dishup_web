package br.com.dishup.environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import br.com.dishup.exception.CidadeNotFoundException;
import br.com.dishup.exception.DatabaseException;
import br.com.dishup.exception.DishUpException;
import br.com.dishup.exception.EmptyTableException;
import br.com.dishup.exception.EstadoAlreadyExistException;
import br.com.dishup.exception.EstadoNotFoundException;
import br.com.dishup.exception.FileEmptyException;
import br.com.dishup.exception.PaisNotFoundException;
import br.com.dishup.exception.TableFieldCheckException;
import br.com.dishup.exception.TableFieldNullValueException;
import br.com.dishup.exception.TableFieldTruncationException;
import br.com.dishup.exception.TableForeignKeyViolationException;
import br.com.dishup.object.EstadoVO;
import br.com.dishup.persistence.ConnectionFactory;
import br.com.dishup.persistence.EstadoDAO;
import br.com.dishup.persistence.PaisDAO;
import br.com.dishup.util.EstadoComparator;
import br.com.dishup.util.StatisticFile;

/**********************************
 * @author Lucas Biccio Ribeiro
 * @since 02/02/2013
 * @version 1.0 Class responsible for load the Estado in system's environment.
 * This class has a main method that should be run only when needs to load Estado
 * To run these method must exist one file with an specific layout into geographic path.
 **********************************/
public class LoadEstadoEnvironment {
	
	/*******************************
	 * Method responsible for load the Estado on system's environment by a balance-line logic
	 * @param filePath
	 * @throws DishUpException 
	 * @throws FileEmptyException 
	 * @throws PaisNotFoundException 
	 * @throws DatabaseException 
	 * @throws EstadoNotFoundException 
	 * @throws CidadeNotFoundException 
	 * @throws TableForeignKeyViolationException 
	 * @throws TableFieldCheckException 
	 * @throws TableFieldNullValueException 
	 * @throws EstadoAlreadyExistException 
	 * @throws TableFieldTruncationException 
	 *******************************/
	public void carregaEstado(String filePath) throws DishUpException{
		File file = new File(filePath);
		StatisticFile statistic = new StatisticFile(new LoadEstadoEnvironment(), "carregaEstado(String filePath)", file, true, "dishup.estado");
		statistic.start();
		ArrayList<EstadoVO> listFile = new ArrayList<EstadoVO>();
		EstadoDAO estadoDAO = new EstadoDAO();
		ConnectionFactory connectionFactory = new ConnectionFactory();
		try{
			listFile = loadFileIntoArray(file);
			Collections.sort(listFile, new EstadoComparator());
			statistic.setNumberOfRegisterFile(listFile.size());
			ArrayList<EstadoVO> listBase = new ArrayList<EstadoVO>();;
			try {
				listBase = estadoDAO.selectAllOrderById(connectionFactory.getConnection());
				statistic.setNumberOfRegisterBase(listBase.size());
				int countListBase = 0, countListFile = 0, numberOfRegisterBase = listBase.size(), numberOfRegisterFile = listFile.size();
				while((countListBase < numberOfRegisterBase) || (countListFile < numberOfRegisterFile)){
					if((countListBase < numberOfRegisterBase) && (countListFile < numberOfRegisterFile)){
						if(listFile.get(countListFile).getId() == listBase.get(countListBase).getId()){
							countListBase++;
							countListFile++;
						}else if(listFile.get(countListFile).getId() > listBase.get(countListBase).getId()){
							estadoDAO.deleteById(connectionFactory.getConnection(),listBase.get(countListBase).getId());
							statistic.incrementRegisterDeleted();
							countListBase++;
						}else{
							estadoDAO.insert(connectionFactory.getConnection(),listFile.get(countListFile));
							statistic.incrementRegisterWrite();
							countListFile++;
						}
					}else if((countListBase < numberOfRegisterBase) && (countListFile >= numberOfRegisterFile)){
						estadoDAO.deleteById(connectionFactory.getConnection(),listBase.get(countListBase).getId());
						statistic.incrementRegisterDeleted();
						countListBase++;
					}else if((countListBase >= numberOfRegisterBase) && (countListFile < numberOfRegisterFile)){
						estadoDAO.insert(connectionFactory.getConnection(),listFile.get(countListFile));
						statistic.incrementRegisterWrite();
						countListFile++;
					}
				}
				statistic.end();
				System.out.println(statistic.toString());
			}catch (EmptyTableException e){
				statistic = new StatisticFile(new LoadEstadoEnvironment(), "carregaEstado(String filePath)", file, false, "");
				statistic.start();
				statistic.setNumberOfRegisterFile(listFile.size());
				for(int i = 0; i < listFile.size(); i++){
					try{
						statistic.incrementRegisterRead();
						estadoDAO.insert(connectionFactory.getConnection(),listFile.get(i));
						statistic.incrementRegisterWrite();
					}catch(Exception e1){
						throw new DishUpException(this.getClass().getName(), "carregaEstado(String filePath)", filePath, e1.getMessage(), e1);
					}
				}
				statistic.end();
				System.out.println(statistic.toString());
			}
		}catch(Throwable e){
			throw new DishUpException(this.getClass().getName(), "carregaEstado(String filePath)", filePath, e.getMessage(), e);
		}
	}
	
	/********************************
	 * Method responsible for create an arrayList with the elements in file
	 * @param filePath
	 * @return {@link ArrayList} of {@link EstadoVO}
	 * @throws FileNotFoundException
	 * @throws FileEmptyException 
	 * @throws DatabaseException 
	 * @throws PaisNotFoundException 
	 * @throws DishUpException 
	 ********************************/
	private ArrayList<EstadoVO> loadFileIntoArray(File file) throws FileNotFoundException, FileEmptyException, PaisNotFoundException, DatabaseException, DishUpException{
		ArrayList<EstadoVO> listaEstado = new ArrayList<EstadoVO>();
		PaisDAO paisDAO = new PaisDAO();
		Scanner scanner = new Scanner(file);
		String var = "";
		String[] parms;
		ConnectionFactory connectionFactory = new ConnectionFactory();
		if(!scanner.hasNext())
			throw new FileEmptyException("Arquivo (caminho: "+file.getPath()+" ) est‡ vazio.");
		while(scanner.hasNext()){
			try{
				var = scanner.nextLine();
				parms = var.split(";");
				listaEstado.add(new EstadoVO(Integer.valueOf(parms[0]), parms[1], parms[2], paisDAO.selectBySigla(connectionFactory.getConnection(),parms[3])));
			}catch(Exception e){
				throw new DishUpException(this.getClass().getName(), "loadFileIntoArray(File file)", file.getPath(), e.getMessage(), e);
			}
		}
		scanner.close();
		return listaEstado;
	}
}
