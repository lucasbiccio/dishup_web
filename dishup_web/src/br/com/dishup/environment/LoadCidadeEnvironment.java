package br.com.dishup.environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import br.com.dishup.exception.CidadeAlreadyExistException;
import br.com.dishup.exception.DatabaseException;
import br.com.dishup.exception.DishUpException;
import br.com.dishup.exception.EmptyTableException;
import br.com.dishup.exception.EstadoNotFoundException;
import br.com.dishup.exception.FileEmptyException;
import br.com.dishup.exception.PaisNotFoundException;
import br.com.dishup.exception.TableFieldCheckException;
import br.com.dishup.exception.TableFieldNullValueException;
import br.com.dishup.exception.TableFieldTruncationException;
import br.com.dishup.exception.TableForeignKeyViolationException;
import br.com.dishup.object.CidadeVO;
import br.com.dishup.persistence.CidadeDAO;
import br.com.dishup.persistence.ConnectionFactory;
import br.com.dishup.persistence.EstadoDAO;
import br.com.dishup.persistence.PaisDAO;
import br.com.dishup.util.CidadeComparator;
import br.com.dishup.util.StatisticFile;

/**********************************
 * @author Lucas Biccio Ribeiro
 * @since 02/02/2013
 * @version 1.0 Class responsible for load the Cidade in system's environment.
 * This class has a main method that should be run only when needs to load Cidade.
 * To run these method must exist one file with an specific layout into geographic path.
 **********************************/
public class LoadCidadeEnvironment {
	
	/***********************************
	 * Method responsible for load the Cidade on system's environment by a balance-line logic
	 * @param filePath - source of file
	 * @throws FileEmptyException 
	 * @throws PaisNotFoundException 
	 * @throws EstadoNotFoundException 
	 * @throws DatabaseException 
	 * @throws TableForeignKeyViolationException 
	 * @throws TableFieldCheckException 
	 * @throws TableFieldNullValueException 
	 * @throws CidadeAlreadyExistException 
	 * @throws TableFieldTruncationException 
	 * @throws DishUpException 
	 ***********************************/
	public void carregaCidade(String filePath) throws DishUpException{
		ConnectionFactory connectionFactory = new ConnectionFactory();
		File file = new File(filePath);
		StatisticFile statistic = new StatisticFile(new LoadCidadeEnvironment(), "carregaCidade(String filePath)", file, true, "dishup.cidade");
		statistic.start();
		ArrayList<CidadeVO> listFile = new ArrayList<CidadeVO>();
		CidadeDAO cidadeDAO = new CidadeDAO();
		try{
			listFile = loadFileIntoArray(file);
			Collections.sort(listFile, new CidadeComparator());
			statistic.setNumberOfRegisterFile(listFile.size());
			ArrayList<CidadeVO> listBase = new ArrayList<CidadeVO>();;
			try {
				listBase = cidadeDAO.selectAllOrderById(connectionFactory.getConnection());
				statistic.setNumberOfRegisterBase(listBase.size());
				int countListBase = 0, countListFile = 0, numberOfRegisterBase = listBase.size(), numberOfRegisterFile = listFile.size();
				while((countListBase < numberOfRegisterBase) || (countListFile < numberOfRegisterFile)){
					if((countListBase < numberOfRegisterBase) && (countListFile < numberOfRegisterFile)){
						if(listFile.get(countListFile).getId() == listBase.get(countListBase).getId()){
							countListBase++;
							countListFile++;
						}else if(listFile.get(countListFile).getId() > listBase.get(countListBase).getId()){
							cidadeDAO.deleteById(connectionFactory.getConnection(),listBase.get(countListBase).getId());
							statistic.incrementRegisterDeleted();
							countListBase++;
						}else{
							cidadeDAO.insert(connectionFactory.getConnection(),listFile.get(countListFile));
							statistic.incrementRegisterWrite();
							countListFile++;
						}
					}else if((countListBase < numberOfRegisterBase) && (countListFile >= numberOfRegisterFile)){
						cidadeDAO.deleteById(connectionFactory.getConnection(),listBase.get(countListBase).getId());
						statistic.incrementRegisterDeleted();
						countListBase++;
					}else if((countListBase >= numberOfRegisterBase) && (countListFile < numberOfRegisterFile)){
						cidadeDAO.insert(connectionFactory.getConnection(),listFile.get(countListFile));
						statistic.incrementRegisterWrite();
						countListFile++;
					}
				}
				statistic.end();
				System.out.println(statistic.toString());
			}catch (EmptyTableException e){
				statistic = new StatisticFile(new LoadCidadeEnvironment(), "carregaCidade(String filePath)", file, false, "");
				statistic.start();
				statistic.setNumberOfRegisterFile(listFile.size());
				for(int i = 0; i < listFile.size(); i++){
					try{
						statistic.incrementRegisterRead();
						cidadeDAO.insert(connectionFactory.getConnection(),listFile.get(i));
						statistic.incrementRegisterWrite();
					}catch(Exception e1){
						throw new DishUpException(this.getClass().getName(), "carregaCidade(String filePath)", filePath, e1.getMessage(), e1);
					}
				}
				statistic.end();
				System.out.println(statistic.toString());
			}
		}catch(Throwable e){
			throw new DishUpException(this.getClass().getName(), "carregaCidade(String filePath)", filePath, e.getMessage(), e);
		}
	}

	/********************************
	 * Method responsible for create an arrayList with the elements in file
	 * @param filePath
	 * @return {@link ArrayList} of {@link Cidade}
	 * @throws FileNotFoundException
	 * @throws FileEmptyException 
	 * @throws PaisNotFoundException 
	 * @throws DatabaseException 
	 * @throws EstadoNotFoundException 
	 * @throws DishUpException 
	 ********************************/
	private ArrayList<CidadeVO> loadFileIntoArray(File file) throws FileNotFoundException, FileEmptyException, DishUpException {
		ArrayList<CidadeVO> listaCidade = new ArrayList<CidadeVO>();
		Scanner scanner = new Scanner(file);
		EstadoDAO estadoDAO = new EstadoDAO();
		PaisDAO paisDAO = new PaisDAO();
		ConnectionFactory connectionFactory = new ConnectionFactory();
		String var = "";
		String[] parms;
		if(!scanner.hasNext())
			throw new FileEmptyException("Arquivo (caminho: "+file.getPath()+" ) est‡ vazio.");
		while(scanner.hasNext()){
			try{
				var = scanner.nextLine();
				parms = var.split(";");
				listaCidade.add(new CidadeVO(Integer.valueOf(parms[0]), parms[1], estadoDAO.selectBySigla(connectionFactory.getConnection(),parms[3]), paisDAO.selectBySigla(connectionFactory.getConnection(),parms[2])));
			}catch(Throwable e){
				System.out.println(var);
				throw new DishUpException(this.getClass().getName(), "loadFileIntoArray(File file)", file.getPath(), e.getMessage(), e);
			}
		}
		scanner.close();
		return listaCidade;
	}
}
