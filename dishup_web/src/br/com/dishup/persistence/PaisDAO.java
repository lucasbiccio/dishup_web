package br.com.dishup.persistence;

import java.sql.Connection;
import java.util.ArrayList;

import br.com.dishup.exception.DatabaseException;
import br.com.dishup.object.PaisVO;

public interface PaisDAO {
	
	public void insert(PaisVO pais);
	
	public PaisVO selectBySigla(String sigla);
	
	public PaisVO selectById(int id);
	
	public ArrayList<PaisVO> selectAllOrderById();
	
	public void deleteById(Connection connection, int id) throws DatabaseException;
}
