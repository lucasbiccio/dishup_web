package br.com.dishup.environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import br.com.dishup.exception.DishUpException;
import br.com.dishup.exception.EmptyTableException;
import br.com.dishup.exception.FileEmptyException;
import br.com.dishup.object.EventoVO;
import br.com.dishup.persistence.ConnectionFactory;
import br.com.dishup.persistence.EventoDAO;
import br.com.dishup.util.EventoComparator;
import br.com.dishup.util.StatisticFile;

public class LoadEventoEnvironment {

	public void carregaEvento(String filePath) throws DishUpException{
		File file = new File(filePath);
		StatisticFile statistic = new StatisticFile(new LoadEventoEnvironment(), "carregaEvento(String filePath)", file, true, "dishup.evento");
		statistic.start();
		ArrayList<EventoVO> listFile = new ArrayList<EventoVO>();
		EventoDAO eventoDAO = new EventoDAO();
		ConnectionFactory connectionFactory = new ConnectionFactory();
		try {
			listFile = loadFileIntoArray(file);
			Collections.sort(listFile, new EventoComparator());
			statistic.setNumberOfRegisterFile(listFile.size());
			ArrayList<EventoVO> listBase = new ArrayList<EventoVO>();
			try{
				listBase = eventoDAO.selectAllOrderById(connectionFactory.getConnection());
				statistic.setNumberOfRegisterBase(listBase.size());
				int countListBase = 0, countListFile = 0, numberOfRegisterBase = listBase.size(), numberOfRegisterFile = listFile.size();
				while((countListBase < numberOfRegisterBase) || (countListFile < numberOfRegisterFile)){
					if((countListBase < numberOfRegisterBase) && (countListFile < numberOfRegisterFile)){
						if(listFile.get(countListFile).getId() == listBase.get(countListBase).getId()){
							countListBase++;
							countListFile++;
						}else if(listFile.get(countListFile).getId() > listBase.get(countListBase).getId()){
							eventoDAO.deleteById(connectionFactory.getConnection(),listBase.get(countListBase).getId());
							statistic.incrementRegisterDeleted();
							countListBase++;
						}else{
							eventoDAO.insert(connectionFactory.getConnection(),listFile.get(countListFile));
							statistic.incrementRegisterWrite();
							countListFile++;
						}
					}else if((countListBase < numberOfRegisterBase) && (countListFile >= numberOfRegisterFile)){
						eventoDAO.deleteById(connectionFactory.getConnection(),listBase.get(countListBase).getId());
						statistic.incrementRegisterDeleted();
						countListBase++;
					}else if((countListBase >= numberOfRegisterBase) && (countListFile < numberOfRegisterFile)){
						eventoDAO.insert(connectionFactory.getConnection(),listFile.get(countListFile));
						statistic.incrementRegisterWrite();
						countListFile++;
					}
				}
				statistic.end();
				System.out.println(statistic.toString());
			}catch(EmptyTableException e){
				statistic = new StatisticFile(new LoadEventoEnvironment(), "carregaEvento(String filePath)", file, false, "");
				statistic.start();
				statistic.setNumberOfRegisterFile(listFile.size());
				for(int i = 0; i < listFile.size(); i++){
					try{
						statistic.incrementRegisterRead();
						eventoDAO.insert(connectionFactory.getConnection(),listFile.get(i));
						statistic.incrementRegisterWrite();
					}catch(Exception e1){
						throw new DishUpException(this.getClass().getName(), "carregaEvento(String filePath)", filePath, e1.getMessage(), e1);
					}
				}
				statistic.end();
				System.out.println(statistic.toString());
			}
		}catch(Throwable e){
			throw new DishUpException(this.getClass().getName(), "carregaEvento(String filePath)", filePath, e.getMessage(), e);
		}
	}
	
	private ArrayList<EventoVO> loadFileIntoArray(File file) throws FileNotFoundException, FileEmptyException, DishUpException{
		ArrayList<EventoVO> list = new ArrayList<EventoVO>();
		Scanner scanner = new Scanner(file);
		String var = "";
		String[] parms;
		if(!scanner.hasNext())
			throw new FileEmptyException("Arquivo (caminho: "+file.getPath()+" ) esta vazio.");
		while(scanner.hasNext()){
			try{
				var = scanner.nextLine();
				parms = var.split(";");
				list.add(new EventoVO(Integer.valueOf(parms[0]), parms[1], parms[2]));
			}catch(Exception e){
				throw new DishUpException(this.getClass().getName(), "loadFileIntoArray(File file)", file.getPath(), e.getMessage(), e);
			}
		}
		scanner.close();
		return list;
	}

}
