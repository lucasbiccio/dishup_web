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
import br.com.dishup.exception.StatusUsuarioAlreadyExistException;
import br.com.dishup.exception.TableFieldCheckException;
import br.com.dishup.exception.TableFieldNullValueException;
import br.com.dishup.object.StatusUsuarioVO;
import br.com.dishup.persistence.ConnectionFactory;
import br.com.dishup.persistence.hibernate.sql.StatusUsuarioDAO;
import br.com.dishup.util.StatisticFile;
import br.com.dishup.util.StatusUsuarioComparator;

public class LoadStatusUsuario {
	
	public void carregaStatusUsuario(String filePath) throws DishUpException, FileEmptyException, DatabaseException, TableFieldCheckException, TableFieldNullValueException, StatusUsuarioAlreadyExistException{
		File file = new File(filePath);
		StatisticFile statistic = new StatisticFile(new LoadStatusUsuario(), "carregaStatusUsuario(String filePath)", file, true, "dishup.status_usuario");
		statistic.start();
		ArrayList<StatusUsuarioVO> listFile = new ArrayList<StatusUsuarioVO>();
		StatusUsuarioDAO statusUsuarioDAO = new StatusUsuarioDAO();
		ConnectionFactory connectionFactory = new ConnectionFactory();
		try {
			listFile = loadFileIntoArray(file);
			Collections.sort(listFile, new StatusUsuarioComparator());
			statistic.setNumberOfRegisterFile(listFile.size());
			ArrayList<StatusUsuarioVO> listBase = new ArrayList<StatusUsuarioVO>();
			try{
				listBase = statusUsuarioDAO.selectAllOrderById(connectionFactory.getConnection());
				statistic.setNumberOfRegisterBase(listBase.size());
				int countListBase = 0, countListFile = 0, numberOfRegisterBase = listBase.size(), numberOfRegisterFile = listFile.size();
				while((countListBase < numberOfRegisterBase) || (countListFile < numberOfRegisterFile)){
					if((countListBase < numberOfRegisterBase) && (countListFile < numberOfRegisterFile)){
						if(listFile.get(countListFile).getIdStatus() == listBase.get(countListBase).getIdStatus()){
							countListBase++;
							countListFile++;
						}else if(listFile.get(countListFile).getIdStatus() > listBase.get(countListBase).getIdStatus()){
							statusUsuarioDAO.deleteById(connectionFactory.getConnection(),listBase.get(countListBase).getIdStatus());
							statistic.incrementRegisterDeleted();
							countListBase++;
						}else{
							statusUsuarioDAO.insert(connectionFactory.getConnection(),listFile.get(countListFile));
							statistic.incrementRegisterWrite();
							countListFile++;
						}
					}else if((countListBase < numberOfRegisterBase) && (countListFile >= numberOfRegisterFile)){
						statusUsuarioDAO.deleteById(connectionFactory.getConnection(),listBase.get(countListBase).getIdStatus());
						statistic.incrementRegisterDeleted();
						countListBase++;
					}else if((countListBase >= numberOfRegisterBase) && (countListFile < numberOfRegisterFile)){
						statusUsuarioDAO.insert(connectionFactory.getConnection(),listFile.get(countListFile));
						statistic.incrementRegisterWrite();
						countListFile++;
					}
				}
				statistic.end();
				System.out.println(statistic.toString());
			}catch(EmptyTableException e){
				statistic = new StatisticFile(new LoadStatusUsuario(), "carregaStatusUsuario(String filePath)", file, false, "");
				statistic.start();
				statistic.setNumberOfRegisterFile(listFile.size());
				for(int i = 0; i < listFile.size(); i++){
					try{
						statistic.incrementRegisterRead();
						statusUsuarioDAO.insert(connectionFactory.getConnection(),listFile.get(i));
						statistic.incrementRegisterWrite();
					}catch(Exception e1){
						throw new DishUpException(this.getClass().getName(), "carregaStatusUsuario(String filePath)", filePath, e1.getMessage(), e1);
					}
				}
				statistic.end();
				System.out.println(statistic.toString());
			}
		}catch(Exception e){
			throw new DishUpException(this.getClass().getName(), "carregaStatusUsuario(String filePath)", filePath, e.getMessage(), e);
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
	private ArrayList<StatusUsuarioVO> loadFileIntoArray(File file) throws FileNotFoundException, FileEmptyException, DishUpException{
		ArrayList<StatusUsuarioVO> lista = new ArrayList<StatusUsuarioVO>();
		Scanner scanner = new Scanner(file);
		String var = "";
		String[] parms;
		if(!scanner.hasNext())
			throw new FileEmptyException("Arquivo (caminho: "+file.getPath()+" ) est‡ vazio.");
		while(scanner.hasNext()){
			try{
				var = scanner.nextLine();
				parms = var.split(";");
				lista.add(new StatusUsuarioVO(Integer.valueOf(parms[0]), parms[1], parms[2]));
			}catch(Exception e){
				throw new DishUpException(this.getClass().getName(), "loadFileIntoArray(File file)", file.getPath(), e.getMessage(), e);
			}
		}
		scanner.close();
		return lista;
	}
}
