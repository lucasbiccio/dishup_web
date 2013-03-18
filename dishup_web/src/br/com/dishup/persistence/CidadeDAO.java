package br.com.dishup.persistence;

import java.sql.Connection;
import java.util.ArrayList;

import br.com.dishup.exception.DatabaseException;
import br.com.dishup.object.CidadeVO;

public interface CidadeDAO {

	public void insert(CidadeVO cidade);
	
	public CidadeVO selectById(int id);
	
	public ArrayList<CidadeVO> selectAllOrderById() ;
	
	public void deleteById(Connection connection, int id) throws DatabaseException;
}
