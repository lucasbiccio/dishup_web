package br.com.dishup.environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import br.com.dishup.exception.DatabaseException;
import br.com.dishup.exception.DishUpException;
import br.com.dishup.exception.EmptyTableException;
import br.com.dishup.exception.FileEmptyException;
import br.com.dishup.exception.PaisAlreadyExistException;
import br.com.dishup.exception.TableFieldCheckException;
import br.com.dishup.exception.TableFieldNullValueException;
import br.com.dishup.exception.TableFieldTruncationException;
import br.com.dishup.object.PaisVO;
import br.com.dishup.persistence.ConnectionFactory;
import br.com.dishup.persistence.PaisDAO;
import br.com.dishup.util.PaisComparator;
import br.com.dishup.util.StatisticFile;


/**********************************
 * @author Lucas Biccio Ribeiro
 * @since 02/02/2013
 * @version 1.0 Class responsible for load the Pais in system's environment.
 * This class has a main method that should be run only when needs to load Pais
 * To run these method must exist one file with an specific layout into geographic path.
 **********************************/
public class LoadPaisEnvironment {

	/***********************************
	 * Method responsible for load the Pais on system's environment by a balance-line logic
	 * @param filePath - source of file
	 * @throws DishUpException 
	 * @throws FileEmptyException 
	 * @throws DatabaseException 
	 * @throws TableFieldCheckException 
	 * @throws TableFieldNullValueException 
	 * @throws PaisAlreadyExistException 
	 * @throws TableFieldTruncationException 
	 ***********************************/
	public void carregaPais(String filePath) throws DishUpException, FileEmptyException, DatabaseException, TableFieldTruncationException, PaisAlreadyExistException, TableFieldNullValueException, TableFieldCheckException{
		File file = new File(filePath);
		StatisticFile statistic = new StatisticFile(new LoadPaisEnvironment(), "carregaPais(String filePath)", file, true, "dishup.pais");
		statistic.start();
		ArrayList<PaisVO> listFile = new ArrayList<PaisVO>();
		PaisDAO paisDAO = new PaisDAO();
		ConnectionFactory connectionFactory = new ConnectionFactory();
		try{
			listFile = loadFileIntoArray(file);
			Collections.sort(listFile, new PaisComparator());
			statistic.setNumberOfRegisterFile(listFile.size());
			ArrayList<PaisVO> listBase = new ArrayList<PaisVO>();;
			try {
				listBase = paisDAO.selectAllOrderById(connectionFactory.getConnection());
				statistic.setNumberOfRegisterBase(listBase.size());
				int countListBase = 0, countListFile = 0, numberOfRegisterBase = listBase.size(), numberOfRegisterFile = listFile.size();
				while((countListBase < numberOfRegisterBase) || (countListFile < numberOfRegisterFile)){
					if((countListBase < numberOfRegisterBase) && (countListFile < numberOfRegisterFile)){
						if(listFile.get(countListFile).getId() == listBase.get(countListBase).getId()){
							countListBase++;
							countListFile++;
						}else if(listFile.get(countListFile).getId() > listBase.get(countListBase).getId()){
							paisDAO.deleteById(connectionFactory.getConnection(),listBase.get(countListBase).getId());
							statistic.incrementRegisterDeleted();
							countListBase++;
						}else{
							paisDAO.insert(connectionFactory.getConnection(),listFile.get(countListFile));
							statistic.incrementRegisterWrite();
							countListFile++;
						}
					}else if((countListBase < numberOfRegisterBase) && (countListFile >= numberOfRegisterFile)){
						paisDAO.deleteById(connectionFactory.getConnection(),listBase.get(countListBase).getId());
						statistic.incrementRegisterDeleted();
						countListBase++;
					}else if((countListBase >= numberOfRegisterBase) && (countListFile < numberOfRegisterFile)){
						paisDAO.insert(connectionFactory.getConnection(),listFile.get(countListFile));
						statistic.incrementRegisterWrite();
						countListFile++;
					}
				}
				statistic.end();
				System.out.println(statistic.toString());
			}catch (EmptyTableException e){
				statistic = new StatisticFile(new LoadPaisEnvironment(), "carregaPais(String filePath)", file, false, "");
				statistic.start();
				statistic.setNumberOfRegisterFile(listFile.size());
				for(int i = 0; i < listFile.size(); i++){
					try{
						statistic.incrementRegisterRead();
						paisDAO.insert(connectionFactory.getConnection(),listFile.get(i));
						statistic.incrementRegisterWrite();
					}catch(Exception e1){
						throw new DishUpException(this.getClass().getName(), "carregaPais(String filePath)", filePath, e1.getMessage(), e1);
					}
				}
				statistic.end();
				System.out.println(statistic.toString());
			}
		}catch(Exception e){
			throw new DishUpException(this.getClass().getName(), "carregaPais(String filePath)", filePath, e.getMessage(), e);
		}
	}
	
	/********************************
	 * Method responsible for create an arrayList with the elements in file
	 * @param filePath
	 * @return {@link ArrayList} of {@link PaisVO}
	 * @throws FileNotFoundException
	 * @throws FileEmptyException 
	 * @throws DishUpException 
	 ********************************/
	private ArrayList<PaisVO> loadFileIntoArray(File file) throws DishUpException, FileEmptyException, FileNotFoundException{
		ArrayList<PaisVO> listaPais = new ArrayList<PaisVO>();
		Scanner scanner = new Scanner(file);
		String var = "";
		String[] parms;
		if(!scanner.hasNext())
			throw new FileEmptyException("Arquivo (caminho: "+file.getPath()+" ) est‡ vazio.");
		while(scanner.hasNext()){
			try{
				var = scanner.nextLine();
				parms = var.split(";");
				listaPais.add(new PaisVO(Integer.valueOf(parms[0]), parms[1], parms[2]));
			}catch(Exception e){
				throw new DishUpException(this.getClass().getName(), "loadFileIntoArray(File file)", file.getPath(), e.getMessage(), e);
			}
		}
		scanner.close();
		return listaPais;
	}
}
