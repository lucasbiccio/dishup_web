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
import br.com.dishup.exception.TableFieldCheckException;
import br.com.dishup.exception.TableFieldNullValueException;
import br.com.dishup.exception.TipoUsuarioAlreadyExistException;
import br.com.dishup.object.TipoUsuarioVO;
import br.com.dishup.persistence.ConnectionFactory;
import br.com.dishup.persistence.TipoUsuarioDAO;
import br.com.dishup.util.StatisticFile;
import br.com.dishup.util.TipoUsuarioComparator;

public class LoadTipoUsuario {

	public void carregaTipoUsuario(String filePath) throws DishUpException, FileEmptyException, DatabaseException, TipoUsuarioAlreadyExistException, TableFieldCheckException, TableFieldNullValueException{
		File file = new File(filePath);
		StatisticFile statistic = new StatisticFile(new LoadTipoUsuario(), "carregaTipoUsuario(String filePath)", file, true, "dishup.tipo_usuario");
		statistic.start();
		ArrayList<TipoUsuarioVO> listFile = new ArrayList<TipoUsuarioVO>();
		TipoUsuarioDAO tipoUsuarioDAO = new TipoUsuarioDAO();
		ConnectionFactory connectionFactory = new ConnectionFactory();
		try {
			listFile = loadFileIntoArray(file);
			Collections.sort(listFile, new TipoUsuarioComparator());
			statistic.setNumberOfRegisterFile(listFile.size());
			ArrayList<TipoUsuarioVO> listBase = new ArrayList<TipoUsuarioVO>();
			try{
				listBase = tipoUsuarioDAO.selectAllOrderById(connectionFactory.getConnection());
				statistic.setNumberOfRegisterBase(listBase.size());
				int countListBase = 0, countListFile = 0, numberOfRegisterBase = listBase.size(), numberOfRegisterFile = listFile.size();
				while((countListBase < numberOfRegisterBase) || (countListFile < numberOfRegisterFile)){
					if((countListBase < numberOfRegisterBase) && (countListFile < numberOfRegisterFile)){
						if(listFile.get(countListFile).getIdTipoUsuario() == listBase.get(countListBase).getIdTipoUsuario()){
							countListBase++;
							countListFile++;
						}else if(listFile.get(countListFile).getIdTipoUsuario() > listBase.get(countListBase).getIdTipoUsuario()){
							tipoUsuarioDAO.deleteById(connectionFactory.getConnection(),listBase.get(countListBase).getIdTipoUsuario());
							statistic.incrementRegisterDeleted();
							countListBase++;
						}else{
							tipoUsuarioDAO.insert(connectionFactory.getConnection(),listFile.get(countListFile));
							statistic.incrementRegisterWrite();
							countListFile++;
						}
					}else if((countListBase < numberOfRegisterBase) && (countListFile >= numberOfRegisterFile)){
						tipoUsuarioDAO.deleteById(connectionFactory.getConnection(),listBase.get(countListBase).getIdTipoUsuario());
						statistic.incrementRegisterDeleted();
						countListBase++;
					}else if((countListBase >= numberOfRegisterBase) && (countListFile < numberOfRegisterFile)){
						tipoUsuarioDAO.insert(connectionFactory.getConnection(),listFile.get(countListFile));
						statistic.incrementRegisterWrite();
						countListFile++;
					}
				}
				statistic.end();
				System.out.println(statistic.toString());
			}catch(EmptyTableException e){
				statistic = new StatisticFile(new LoadTipoUsuario(), "carregaTipoUsuario(String filePath)", file, false, "");
				statistic.start();
				statistic.setNumberOfRegisterFile(listFile.size());
				for(int i = 0; i < listFile.size(); i++){
					try{
						statistic.incrementRegisterRead();
						tipoUsuarioDAO.insert(connectionFactory.getConnection(),listFile.get(i));
						statistic.incrementRegisterWrite();
					}catch(Exception e1){
						throw new DishUpException(this.getClass().getName(), "carregaTipoUsuario(String filePath)", filePath, e1.getMessage(), e1);
					}
				}
				statistic.end();
				System.out.println(statistic.toString());
			}
		}catch(Exception e){
			throw new DishUpException(this.getClass().getName(), "carregaTipoUsuario(String filePath)", filePath, e.getMessage(), e);
		}
	}
	
	private ArrayList<TipoUsuarioVO> loadFileIntoArray(File file) throws FileNotFoundException, FileEmptyException, DishUpException{
		ArrayList<TipoUsuarioVO> lista = new ArrayList<TipoUsuarioVO>();
		Scanner scanner = new Scanner(file);
		String var = "";
		String[] parms;
		if(!scanner.hasNext())
			throw new FileEmptyException("Arquivo (caminho: "+file.getPath()+" ) esta vazio.");
		while(scanner.hasNext()){
			try{
				var = scanner.nextLine();
				parms = var.split(";");
				lista.add(new TipoUsuarioVO(Integer.valueOf(parms[0]), parms[1], parms[2]));
			}catch(Exception e){
				throw new DishUpException(this.getClass().getName(), "loadFileIntoArray(File file)", file.getPath(), e.getMessage(), e);
			}
		}
		scanner.close();
		return lista;
	}
}
