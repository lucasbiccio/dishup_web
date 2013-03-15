package br.com.dishup.environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import br.com.dishup.exception.DishUpException;
import br.com.dishup.exception.EmptyTableException;
import br.com.dishup.exception.FileEmptyException;
import br.com.dishup.object.CanalVO;
import br.com.dishup.persistence.CanalDAO;
import br.com.dishup.persistence.ConnectionFactory;
import br.com.dishup.util.CanalComparator;
import br.com.dishup.util.StatisticFile;

public class LoadCanalEnvironment {
	
	public void carregaCanal(String filePath) throws DishUpException{
		ConnectionFactory connectionFactory = new ConnectionFactory();
		File file = new File(filePath);
		StatisticFile statistic = new StatisticFile(new LoadCanalEnvironment(), "carregaCanal(String filePath)", file, true, "dishup.canal");
		statistic.start();
		ArrayList<CanalVO> listFile = new ArrayList<CanalVO>();
		CanalDAO canalDAO = new CanalDAO();
		try {
			listFile = loadFileIntoArray(file);
			Collections.sort(listFile, new CanalComparator());
			statistic.setNumberOfRegisterFile(listFile.size());
			ArrayList<CanalVO> listBase = new ArrayList<CanalVO>();
			try{
				listBase = canalDAO.selectAllOrderById(connectionFactory.getConnection());
				statistic.setNumberOfRegisterBase(listBase.size());
				int countListBase = 0, countListFile = 0, numberOfRegisterBase = listBase.size(), numberOfRegisterFile = listFile.size();
				while((countListBase < numberOfRegisterBase) || (countListFile < numberOfRegisterFile)){
					if((countListBase < numberOfRegisterBase) && (countListFile < numberOfRegisterFile)){
						if(listFile.get(countListFile).getId() == listBase.get(countListBase).getId()){
							countListBase++;
							countListFile++;
						}else if(listFile.get(countListFile).getId() > listBase.get(countListBase).getId()){
							canalDAO.deleteById(connectionFactory.getConnection(),listBase.get(countListBase).getId());
							statistic.incrementRegisterDeleted();
							countListBase++;
						}else{
							canalDAO.insert(connectionFactory.getConnection(),listFile.get(countListFile));
							statistic.incrementRegisterWrite();
							countListFile++;
						}
					}else if((countListBase < numberOfRegisterBase) && (countListFile >= numberOfRegisterFile)){
						canalDAO.deleteById(connectionFactory.getConnection(),listBase.get(countListBase).getId());
						statistic.incrementRegisterDeleted();
						countListBase++;
					}else if((countListBase >= numberOfRegisterBase) && (countListFile < numberOfRegisterFile)){
						canalDAO.insert(connectionFactory.getConnection(),listFile.get(countListFile));
						statistic.incrementRegisterWrite();
						countListFile++;
					}
				}
				statistic.end();
				System.out.println(statistic.toString());
			}catch(EmptyTableException e){
				statistic = new StatisticFile(new LoadCanalEnvironment(), "carregaCanal(String filePath)", file, false, "");
				statistic.start();
				statistic.setNumberOfRegisterFile(listFile.size());
				for(int i = 0; i < listFile.size(); i++){
					try{
						statistic.incrementRegisterRead();
						canalDAO.insert(connectionFactory.getConnection(),listFile.get(i));
						statistic.incrementRegisterWrite();
					}catch(Exception e1){
						throw new DishUpException(this.getClass().getName(), "carregaCanal(String filePath)", filePath, e1.getMessage(), e1);
					}
				}
				statistic.end();
				System.out.println(statistic.toString());
			}
		}catch(Throwable e){
			throw new DishUpException(this.getClass().getName(), "carregaCanal(String filePath)", filePath, e.getMessage(), e);
		}
	}
	
	private ArrayList<CanalVO> loadFileIntoArray(File file) throws FileNotFoundException, FileEmptyException, DishUpException{
		ArrayList<CanalVO> lista = new ArrayList<CanalVO>();
		Scanner scanner = new Scanner(file);
		String var = "";
		String[] parms;
		if(!scanner.hasNext())
			throw new FileEmptyException("Arquivo (caminho: "+file.getPath()+" ) esta vazio.");
		while(scanner.hasNext()){
			try{
				var = scanner.nextLine();
				parms = var.split(";");
				lista.add(new CanalVO(Integer.valueOf(parms[0]), parms[1], parms[2]));
			}catch(Exception e){
				throw new DishUpException(this.getClass().getName(), "loadFileIntoArray(File file)", file.getPath(), e.getMessage(), e);
			}
		}
		scanner.close();
		return lista;
	}
}
